package ua.sumdu.yermolenko.constants;

import org.springframework.stereotype.Component;

/**
 * Class ServiceConstants contains service constants.
 *
 * @author Andrey
 * Created on 22.02.2020
 */
@Component
public class ServiceConstants {
    private String messageDoesNotSupportField = "Api does not support this field.";

    /**
     * Method getMessageDoesNotSupportField returns the messageDoesNotSupportField of this ServiceConstants object.
     *
     * @return the messageDoesNotSupportField (type String) of this ServiceConstants object.
     */
    public String getMessageDoesNotSupportField() {
        return messageDoesNotSupportField;
    }
}

