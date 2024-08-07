import api from "src/axiosInstance";
import {message} from "antd";

const RecordUploadImg = async (values) => {
  try {
    const token = localStorage.getItem('jwtManageToken');
    const formData = new FormData();

    // 处理文件
    if (values.file && values.file.length > 0) {
      values.file.forEach((file) => {
        if (file && file.originFileObj) {
          formData.append('file', file.originFileObj);
        }
      });
    }

    // 处理其他字段
    Object.keys(values).forEach((key) => {
      if (key !== 'file') {
        formData.append(key, key === 'photoTime' ? values[key].format("YYYY-MM-DDTHH:mm:ss") : values[key]);
      }
    });

    await api.post('/manage/record/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        authorization: `${token}`,
      },
    });

    message.success('Upload successful!');
  } catch (error) {
    console.error('Failed to upload image', error);
    message.error('Upload failed!');
  }
};

export { RecordUploadImg }
