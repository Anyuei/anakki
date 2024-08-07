import React, { useState, useEffect } from 'react';
import api from 'src/axiosInstance';
import 'src/scss/_custom.scss';
import { Modal, Button, Form, Input, DatePicker, Upload, message, Spin } from 'antd';
import Pagination from 'src/components/common/Pagination';
import { UploadOutlined } from '@ant-design/icons';
import { HandleBatchDelete } from 'src/components/common/HandleBatchDelete';
import { UseSelectableRows } from 'src/components/common/UseSelectableRows'
import { RecordUploadImg } from './RecordUploadImg'
// 更新图片状态
const updateImageStatus = async (id, newStatus) => {
  try {
    const token = localStorage.getItem('jwtManageToken');
    await api.post(
        '/manage/record/change-status',
        { id, isChecked: newStatus === 'COMMON' },
        { headers: { authorization: `${token}` } },
    );
    return true;
  } catch (error) {
    console.error('Failed to update image status', error);
    return false;
  }
};

const RecordList = () => {
  const [data, setData] = useState([]);
  const [totalNum, setTotalNum] = useState(0);
  const [current, setCurrent] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [searchParams, setSearchParams] = useState({
    type: '',
    title: '',
    location: '',
    description: '',
    imgUrl: '',
    photoTimeStart: '',
    photoTimeEnd: '',
    createTimeStart: '',
    createTimeEnd: '',
    photoBy: '',
  });

  const [isUploadModalVisible, setIsUploadModalVisible] = useState(false);
  const [isImageModalVisible, setIsImageModalVisible] = useState(false);
  const [fullscreenImage, setFullscreenImage] = useState(null);
  const [isLoading, setIsLoading] = useState(false); // 添加加载状态
  const [form] = Form.useForm();

  useEffect(() => {
    fetchData();
  }, [current, pageSize, searchParams]);

  const {
    selectedRows,
    selectAll,
    handleSelectAll,
    handleSelectRow,
  } = UseSelectableRows();

  const fetchData = async () => {
    setIsLoading(true); // 开始加载
    try {
      const token = localStorage.getItem('jwtManageToken');
      const filteredParams = Object.fromEntries(
          Object.entries(searchParams).filter(([_, value]) => value !== '' && value !== null),
      );
      const response = await api.get('/manage/record/list', {
        params: { current, size: pageSize, ...filteredParams },
        headers: { authorization: `${token}` },
      });

      setData(response.data.data.data);
      setTotalNum(response.data.data.totalNum);
    } catch (error) {
      console.error('Failed to fetch data', error);
    } finally {
      setIsLoading(false); // 完成加载
    }
  };
  const handleStatusChange = async (id, event) => {
    const newStatus = event.target.checked ? 'COMMON' : 'INVALID';
    const success = await updateImageStatus(id, newStatus);
    if (success) {
      const updatedData = data.map((item) =>
          item.id === id
              ? { ...item, status: item.status === 'COMMON' ? 'INVALID' : 'COMMON' }
              : item,
      );
      setData(updatedData);
    }
  };

  const handleSearchChange = (event) => {
    const { name, value } = event.target;
    setSearchParams((prevParams) => ({ ...prevParams, [name]: value }));
  };

  const uploadImg = async (values) => {
    await RecordUploadImg(values);
    setIsUploadModalVisible(false);
    form.resetFields();
  };

  const handleImageClick = (imgUrl) => {
    const urlWithoutParams = imgUrl.split('?')[0];
    setFullscreenImage(urlWithoutParams);
    setIsImageModalVisible(true);
  };

  const handleUploadModalCancel = () => setIsUploadModalVisible(false);

  const handleImageModalCancel = () => {
    setIsImageModalVisible(false);
    setFullscreenImage(null);
  };

  const totalPages = Math.ceil(totalNum / pageSize);

  return (
      <div>
        <div className="mb-3">
          <div className="search-container">
            {Object.keys(searchParams).map((key) => (
                <div key={key} className="position-relative">
                  <input
                      type={key.includes('Time') ? 'datetime-local' : 'text'}
                      className="form-control search-box"
                      name={key}
                      placeholder={`搜索${key}`}
                      value={searchParams[key]}
                      onChange={handleSearchChange}
                  />
                </div>
            ))}
            <Button
                type="primary"
                onClick={fetchData}
                className="search-button"
                disabled={isLoading} // 禁用按钮
            >
              {isLoading ? <Spin /> : '查询'}
            </Button>
            <Button
                type="primary"
                onClick={() => setIsUploadModalVisible(true)}
                className="upload-button"
            >
              上传图片
            </Button>
            <Button
                type="danger"
                onClick={() => HandleBatchDelete({
                  url: '/manage/record/delete-batch',
                  selectedRows,
                  fetchData,
                })}
                disabled={selectedRows.length === 0}
            >
              批量删除
            </Button>
          </div>
        </div>

        <Modal
            title="上传图片"
            open={isUploadModalVisible}
            onCancel={handleUploadModalCancel}
            footer={null}
            zIndex={1000}
        >
          <Form form={form} onFinish={uploadImg} layout="vertical">
            {['type', 'title', 'location', 'photoBy', 'imageSize', 'description'].map((field) => (
                <Form.Item key={field} name={field} label={field === 'imageSize' ? '分辨率' : field}>
                  <Input placeholder={`请输入${field}`} />
                </Form.Item>
            ))}
            <Form.Item
                name="photoTime"
                label="拍摄时间"
                rules={[{ required: true, message: '请选择拍摄时间' }]}
            >
              <DatePicker showTime />
            </Form.Item>
            <Form.Item
                name="file"
                label="选择文件"
                valuePropName="fileList"
                getValueFromEvent={(e) => e && e.fileList}
                rules={[{ required: true, message: '请选择文件' }]}
            >
              <Upload beforeUpload={() => false} multiple>
                <Button icon={<UploadOutlined />}>选择文件</Button>
              </Upload>
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit">
                上传
              </Button>
            </Form.Item>
          </Form>
        </Modal>

        <Modal
            open={isImageModalVisible}
            footer={null}
            onCancel={handleImageModalCancel}
            width="80%"
            style={{ top: 20 }}
            zIndex={2000}
        >
          {fullscreenImage && (
              <img src={fullscreenImage} alt="fullscreen" style={{ width: '100%' }} />
          )}
        </Modal>

        <div className="table-responsive">
          <Spin spinning={isLoading}>
            <table className="table table-bordered table-striped">
              <thead>
              <tr>
                <th>
                  <div className="custom-control custom-checkbox">
                    <input
                        type="checkbox"
                        className="custom-control-input"
                        id="select_all"
                        checked={selectAll}
                        onChange={handleSelectAll}
                    />
                    <label className="custom-control-label" htmlFor="select_all"></label>
                  </div>
                </th>
                {[
                  'ID',
                  '类型',
                  '标题',
                  '拍摄位置',
                  '描述',
                  '拍摄者',
                  '尺寸',
                  '文件大小',
                  '状态',
                  '图片信息',
                  '操作',
                ].map((field) => (
                    <th key={field}>{field}</th>
                ))}
              </tr>
              </thead>
              <tbody>
              {data.map((item) => (
                  <tr key={item.id} className="record-font">
                    <td>
                      <input
                          type="checkbox"
                          checked={selectedRows.includes(item.id)}
                          onChange={() => handleSelectRow(item.id, data)}
                      />
                    </td>
                    <td className="text-truncate">{item.id}</td>
                    <td className="text-truncate">{item.type}</td>
                    <td className="text-truncate">{item.title}</td>
                    <td className="text-truncate">{item.location}</td>
                    <td className="text-truncate">{item.description}</td>
                    <td className="text-truncate">{item.photoBy}</td>
                    <td className="text-truncate">{item.imageSize}</td>
                    <td className="text-truncate">{(item.fileSize / 1024).toFixed(2)}MB</td>
                    <td>
                      <label className="toggle-switch">
                        <input
                            type="checkbox"
                            checked={item.status === 'COMMON'}
                            onChange={(e) => handleStatusChange(item.id, e)}
                        />
                        <span className="toggle-switch-slider"></span>
                      </label>
                    </td>
                    <td className="text-truncate">
                      <div className="media align-items-center">
                        <div className="image-list">
                          <img
                              src={`${item.imgUrl}`}
                              className="golden-ratio-image"
                              alt="thumbnail"
                              onClick={() => handleImageClick(item.imgUrl)}
                          />
                        </div>
                        <div className="media-body">
                        <span className="vertical-span">
                          拍摄时间：{item.photoTime.replace('T', ' ')}
                        </span>
                          <span className="vertical-span">
                          上传时间：{item.createTime.replace('T', ' ')}
                        </span>
                          <span className="vertical-span">
                          修改时间：{item.updateTime.replace('T', ' ')}
                        </span>
                        </div>
                      </div>
                    </td>
                    <td>
                      <button className="btn btn-danger" onClick={() => handleDelete(item.id)}>
                        删除
                      </button>
                    </td>
                  </tr>
              ))}
              </tbody>
            </table>
          </Spin>
        </div>
        <Pagination
            totalPages={totalPages}
            current={current}
            onPageChange={(page) => setCurrent(page)}
        />
      </div>
  );
};

export default RecordList;
