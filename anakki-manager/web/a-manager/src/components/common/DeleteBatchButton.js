import api from 'src/axiosInstance';
import {Modal} from "antd";

const DeleteBatchButton = async (endpoint, ids) => {
    try {
        const token = localStorage.getItem('jwtManageToken')

        if (!token) {
            throw new Error('No authorization token found')
        }
        const response = await api.delete(endpoint, {
            data: { idList: ids },
            headers: { authorization: `Bearer ${token}` },
        })

        if (response.status !== 200) {
            throw new Error(`Error: ${response.status} - ${response.statusText}`)
        }

        return response.data // 可以返回响应数据，供调用者使用

    } catch (error) {
        // 更详细的错误信息
        console.error(`Failed to delete items from ${endpoint}`, error)
        // 这里可以进一步处理错误，例如显示错误提示给用户
        throw error
    }
}

export { DeleteBatchButton }
