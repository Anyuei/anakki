import React, { useState, useEffect } from 'react'
import api from 'src/axiosInstance'
import 'src/scss/_custom.scss'
import { Modal, Button, Form, Input, DatePicker, Upload, message, Spin } from 'antd'
import { UploadOutlined } from '@ant-design/icons'

// 示例函数: 用于更新图片状态
const updateImageStatus = async (id, newStatus) => {
  try {
    const token = localStorage.getItem('jwtManageToken')
    await api.post(
      '/manage/record/change-status',
      { id, isChecked: newStatus === 'COMMON' },
      { headers: { authorization: `${token}` } },
    )
    return true
  } catch (error) {
    console.error('Failed to update image status', error)
    return false
  }
}

// 示例函数: 用于删除项目
const deleteItem = async (id) => {
  try {
    const token = localStorage.getItem('jwtManageToken')
    await api.delete(`/manage/record/delete/${id}`, { headers: { authorization: `${token}` } })
  } catch (error) {
    console.error('Failed to delete item', error)
  }
}

// 上传图片
const uploadImage = async (values) => {
  try {
    const token = localStorage.getItem('jwtManageToken')
    const formData = new FormData()
    Object.keys(values).forEach((key) => {
      if (key === 'file') {
        values[key].forEach((file) => formData.append('file', file.file.originFileObj))
      } else {
        formData.append(key, values[key])
      }
    })

    await api.post('/manage/record/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        authorization: `${token}`,
      },
    })

    message.success('Upload successful!')
  } catch (error) {
    console.error('Failed to upload image', error)
    message.error('Upload failed!')
  }
}

const RecordList = () => {
  const [data, setData] = useState([])
  const [totalNum, setTotalNum] = useState(0)
  const [current, setCurrent] = useState(1)
  const [pageSize, setPageSize] = useState(10)
  const [selectAll, setSelectAll] = useState(false)
  const [selectedRows, setSelectedRows] = useState([])
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
  })

  const [isUploadModalVisible, setIsUploadModalVisible] = useState(false)
  const [isImageModalVisible, setIsImageModalVisible] = useState(false)
  const [fullscreenImage, setFullscreenImage] = useState(null)
  const [isLoading, setIsLoading] = useState(false) // 添加加载状态
  const [form] = Form.useForm()

  useEffect(() => {
    fetchData()
  }, [current, pageSize, searchParams])
  const fetchData = async () => {
    setIsLoading(true) // 开始加载
    try {
      const token = localStorage.getItem('jwtManageToken')
      const filteredParams = Object.fromEntries(
        Object.entries(searchParams).filter(([_, value]) => value !== '' && value !== null),
      )
      const response = await api.get('/manage/record/list', {
        params: { current, size: pageSize, ...filteredParams },
        headers: { authorization: `${token}` },
      })

      setData(response.data.data.data)
      setTotalNum(response.data.data.totalNum)
    } catch (error) {
      console.error('Failed to fetch data', error)
    } finally {
      setIsLoading(false) // 完成加载
    }
  }
  const handleStatusChange = async (id, event) => {
    const newStatus = event.target.checked ? 'COMMON' : 'INVALID'
    const success = await updateImageStatus(id, newStatus)
    if (success) {
      const updatedData = data.map((item) =>
        item.id === id
          ? { ...item, status: item.status === 'COMMON' ? 'INVALID' : 'COMMON' }
          : item,
      )
      setData(updatedData)
    }
  }

  const handleDelete = (id) => deleteItem(id)

  const handleSelectAll = (event) => {
    const checked = event.target.checked
    setSelectAll(checked)
    setSelectedRows(checked ? data.map((item) => item.id) : [])
  }

  const handleSelectRow = (id) => {
    const newSelectedRows = selectedRows.includes(id)
      ? selectedRows.filter((rowId) => rowId !== id)
      : [...selectedRows, id]
    setSelectedRows(newSelectedRows)
    setSelectAll(newSelectedRows.length === data.length)
  }

  const handleSearchChange = (event) => {
    const { name, value } = event.target
    setSearchParams((prevParams) => ({ ...prevParams, [name]: value }))
  }

  const handleSearch = () => {
    fetchData()
  }

  const handleUpload = async (values) => {
    await uploadImage(values)
    setIsUploadModalVisible(false)
    form.resetFields()
  }

  const handleImageClick = (imgUrl) => {
    const urlWithoutParams = imgUrl.split('?')[0]
    setFullscreenImage(urlWithoutParams)
    setIsImageModalVisible(true)
  }

  const handleUploadModalCancel = () => setIsUploadModalVisible(false)

  const handleImageModalCancel = () => {
    setIsImageModalVisible(false)
    setFullscreenImage(null)
  }

  const totalPages = Math.ceil(totalNum / pageSize)

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
            onClick={handleSearch}
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
        </div>
      </div>

      <Modal
        title="上传图片"
        visible={isUploadModalVisible}
        onCancel={handleUploadModalCancel}
        footer={null}
        zIndex={1000}
      >
        <Form form={form} onFinish={handleUpload} layout="vertical">
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
            getValueFromEvent={({ file }) => (file ? [file] : [])}
            rules={[{ required: true, message: '请选择文件' }]}
          >
            <Upload beforeUpload={() => false} showUploadList={false} accept="image/*">
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
        visible={isImageModalVisible}
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
                    <div className="custom-control custom-checkbox">
                      <input
                        type="checkbox"
                        className="custom-control-input"
                        id={`td_checkbox_${item.id}`}
                        checked={selectedRows.includes(item.id)}
                        onChange={() => handleSelectRow(item.id)}
                      />
                      <label
                        className="custom-control-label"
                        htmlFor={`td_checkbox_${item.id}`}
                      ></label>
                    </div>
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

      <nav aria-label="Page navigation">
        <ul className="pagination">
          {Array.from({ length: totalPages }, (_, index) => (
            <li key={index} className={`page-item ${current === index + 1 ? 'active' : ''}`}>
              <button className="page-link" onClick={() => setCurrent(index + 1)}>
                {index + 1}
              </button>
            </li>
          ))}
        </ul>
      </nav>
    </div>
  )
}

export default RecordList
