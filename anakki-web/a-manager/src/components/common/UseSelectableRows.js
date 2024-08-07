import { useState } from 'react';

// 通用选择行方法
export const UseSelectableRows = () => {
    //所有单选按钮数据存储
    const [selectedRows, setSelectedRows] = useState([]);
    //全选按钮数据存储，默认false
    const [selectAll, setSelectAll] = useState(false);

    //处理全选逻辑
    const handleSelectAll = (event, data) => {
        const checked = event.target.checked;
        setSelectAll(checked);
        setSelectedRows(checked ? data.map((item) => item.id) : []);
    };
    //处理单选逻辑
    const handleSelectRow = (id, data) => {
        const newSelectedRows = selectedRows.includes(id)
            ? selectedRows.filter((rowId) => rowId !== id)
            : [...selectedRows, id];
        //刷新已选择的id
        setSelectedRows(newSelectedRows);
        //如果单选被全部选中，全选框置为选中
        setSelectAll(newSelectedRows.length === data.length);
    };

    return {
        selectedRows,
        selectAll,
        handleSelectAll,
        handleSelectRow,
    };
};
