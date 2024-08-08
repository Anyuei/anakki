import React, { useState } from 'react';
import axios from 'axios';
import COS from 'cos-js-sdk-v5';
import { Upload, Button, message, Spin } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import api from 'src/axiosInstance';

const FileUpload = ({ onUploadSuccess, onUploadError ,onUploadStatusChange, onUploadProgress}) => {
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
        onUploadStatusChange(true); // 上传开始

        try {
            const { secretId, secretKey, sessionToken, host } = await getTemporaryCredentials();
            const match = host.match(/https:\/\/([^\.]+)\.cos\.([^.]+)\.myqcloud\.com\//);

            if (match) {
                const Bucket = match[1];
                const Region = match[2];

                const cos = new COS({
                    SecretId: secretId,
                    SecretKey: secretKey,
                    SecurityToken: sessionToken,
                });

                const uploadPromises = fileList.map((file) => {
                    const fileName = `images/${file.name}`;
                    const params = {
                        Bucket,
                        Region,
                        Key: fileName,
                        Body: file,
                        onProgress: (progressData) => {
                            const percentCompleted = (progressData.percent * 100).toFixed(2);
                            const speed = (progressData.speed / 1024).toFixed(2); // KB/s
                            onUploadProgress(percentCompleted, speed);
                        },
                    };

                    return new Promise((resolve, reject) => {
                        cos.putObject(params, (err, data) => {
                            if (err) {
                                reject(err);
                            } else {
                                resolve({
                                    url: `https://${Bucket}.cos.${Region}.myqcloud.com/${fileName}`,
                                    name: file.name,
                                });
                            }
                        });
                    });
                });

                const results = await Promise.all(uploadPromises);
                const fileUrls = results.map((result) => result.url);
                const fileNames = results.map((result) => result.name);
                onUploadSuccess(fileUrls, fileNames);
                message.success('文件上传成功');
            } else {
                throw new Error('URL format is incorrect.');
            }
        } catch (error) {
            console.error('Failed to upload files', error);
            onUploadError(error);
        } finally {
            setLoading(false);
            onUploadStatusChange(false); // 上传结束
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
