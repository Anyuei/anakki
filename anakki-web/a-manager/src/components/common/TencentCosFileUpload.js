import React, { useState } from 'react';
import axios from 'axios';
import COS from 'cos-js-sdk-v5';
import { Upload, Button, message, Spin } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import api from 'src/axiosInstance';
const FileUpload = ({ onUploadSuccess, onUploadError }) => {
    const [loading, setLoading] = useState(false);

    // 获取临时密钥
    const getTemporaryCredentials = async () => {
        try {
            const token = localStorage.getItem('jwtManageToken');
            const response = await api.get('/manage/manager/getCosCredential', {
                headers: { Authorization: `Bearer ${token}` },
            });
            return response.data.data;
        } catch (error) {
            console.error('Failed to fetch temporary credentials', error);
            throw error;
        }
    };

    // 处理文件上传
    const handleUpload = async (fileList) => {
        setLoading(true);
        try {
            const { secretId, secretKey, sessionToken, host } = await getTemporaryCredentials();
            const match = host.match(/https:\/\/([^\.]+)\.cos\.([^.]+)\.myqcloud\.com\//);

            if (match) {
                const Bucket = match[1];
                const Region = match[2];
                console.log(`Bucket Name: ${Bucket}`);
                console.log(`Region: ${Region}`);

                const cos = new COS({
                    SecretId: secretId,
                    SecretKey: secretKey,
                    SecurityToken: sessionToken,
                    // 添加以下配置来处理跨源请求
                    uploadFile: {
                        credentials: {
                            accessKeyId: secretId,
                            secretAccessKey: secretKey
                        }
                    }
                });

                const uploadPromises = fileList.map((file) => {
                    const fileName = `images/${file.name}`;
                    const params = {
                        Bucket,
                        Region,
                        Key: fileName,
                        Body: file,
                    };

                    return new Promise((resolve, reject) => {
                        cos.putObject(params, (err, data) => {
                            if (err) {
                                reject(err);
                            } else {
                                resolve(`https://${Bucket}.cos.${Region}.myqcloud.com/${fileName}`);
                            }
                        });
                    });
                });

                const fileUrls = await Promise.all(uploadPromises);
                onUploadSuccess(fileUrls);
                message.success('文件上传成功');
            } else {
                console.log('URL format is incorrect.');
            }
        } catch (error) {
            console.error('Failed to upload files', error);
            onUploadError(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <Upload
            customRequest={({ file, onSuccess, onError }) => {
                handleUpload([file])
                    .then(() => onSuccess())
                    .catch(onError);
            }}
            multiple
            showUploadList={false}
        >
            <Button icon={<UploadOutlined />} disabled={loading}>
                {loading ? <Spin /> : 'Upload Files'}
            </Button>
        </Upload>
    );
};

export default FileUpload;
