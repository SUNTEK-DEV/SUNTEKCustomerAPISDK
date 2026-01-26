package com.suntek.mylibrary;

/**
 * 设备类型枚举
 * 定义支持的设备型号
 */
public enum DeviceType {
    FS8("FS8", "FS8 device"),
    FS10("FS10", "FS10 device"),
    SK8("3576SE", "SK8 device"),//即SK8
    SC1("SC1", "SC1 device"),
    SC1_("LP", "SC1 device"),
    FA8("FA8","FA8 device"),
    FA10("FA10","FA10 device"),
    SK27E("SK27-E","SK27-E device"),

    UNKNOWN("UNKNOWN", "Unknown device");
    
    private final String model;
    private final String description;
    
    DeviceType(String model, String description) {
        this.model = model;
        this.description = description;
    }
    
    public String getModel() {
        return model;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Obtain device type based on device model
     * @param model Device Model
     * @return Device Type
     */
    public static DeviceType fromModel(String model) {
        if (model == null) {
            return UNKNOWN;
        }
        
        for (DeviceType type : values()) {
            if (type.getModel().equalsIgnoreCase(model.trim())) {
                return type;
            }
        }
        return UNKNOWN;
    }
} 