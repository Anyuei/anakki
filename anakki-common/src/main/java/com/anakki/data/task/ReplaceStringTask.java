package com.anakki.data.task;

import lombok.Data;

import java.util.HashMap;

/**
 * ClassName: ReplaceStringTask
 * Description:
 *
 * @author Anakki
 * @date 2023/5/28 22:20
 */
@Data
public class ReplaceStringTask extends ProcessTask implements Runnable {

    private Long taskId;

    private String replaceString="*";

    private HashMap<String,StringBuffer> sourceStringList;

    private Integer start;

    private Integer end;
    @Override
    public void run() {
        if (null!=sourceStringList&&!sourceStringList.isEmpty()) {
            sourceStringList.forEach((k,stringBuffer) -> {
                if (start>=end||end<=0){
                    return;
                }
                if (start>=stringBuffer.length()){
                    stringBuffer.replace(start, end, replaceString);
                }
            });
        }
        completed=true;
    }
}
