package com.hunganh.mymoments.base;

import com.hunganh.mymoments.dto.SnwMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * @Author: Tran Tuan Anh
 * @Created: Mon, 03/05/2021 9:31 AM
 **/

@Data
@Async
@AllArgsConstructor
@NoArgsConstructor
public class ConsumerBase {
    protected SnwObjectType objectType;
    protected SnwActionType actionType;

    public boolean isExistEvent(SnwObjectType objectType, SnwActionType actionType) {
        return (this.objectType == objectType
                && this.actionType == actionType);
    }

    public void init(SnwMessage messageEvent) {
        objectType = messageEvent.getObjectType();
        actionType = messageEvent.getActionType();
    }
}
