package biz.gelicon.capital.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Единица измерения DTO")
public class UnitmeasureDTO {

    @Schema(description = "Первичный ключ")
    private Integer unitmeasureId;

    @Schema(description = "Наименование")
    private String unitmeasureName;

    @Schema(description = "Обозначение", example = "кг.")
    private String unitmeasureShortname;

    @Schema(description = "ИД связи с мерой измерения - measureunit_id")
    private Integer measureunitId;

    @Schema(description = "Приоритет связи с мерой измерения - measureunit_priority")
    private Integer measureunitPriority;

    @Schema(description = "Мера измерения measure_id")
    private Integer measureId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String measureName;

    public UnitmeasureDTO(Integer unitmeasureId, String unitmeasureName,
            String unitmeasureShortname, Integer measureunitId, Integer measureunitPriority,
            Integer measureId, String measureName) {
        this.unitmeasureId = unitmeasureId;
        this.unitmeasureName = unitmeasureName;
        this.unitmeasureShortname = unitmeasureShortname;
        this.measureunitId = measureunitId;
        this.measureunitPriority = measureunitPriority;
        this.measureId = measureId;
        this.measureName = measureName;
    }

    public UnitmeasureDTO() {
    }

    public Integer getUnitmeasureId() {
        return unitmeasureId;
    }

    public String getUnitmeasureName() {
        return unitmeasureName;
    }

    public String getUnitmeasureShortname() {
        return unitmeasureShortname;
    }

    public Integer getMeasureunitId() {
        return measureunitId;
    }

    public Integer getMeasureunitPriority() {
        return measureunitPriority;
    }

    public Integer getMeasureId() {
        return measureId;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setUnitmeasureId(Integer unitmeasureId) {
        this.unitmeasureId = unitmeasureId;
    }

    public void setUnitmeasureName(String unitmeasureName) {
        this.unitmeasureName = unitmeasureName;
    }

    public void setUnitmeasureShortname(String unitmeasureShortname) {
        this.unitmeasureShortname = unitmeasureShortname;
    }

    public void setMeasureunitId(Integer measureunitId) {
        this.measureunitId = measureunitId;
    }

    public void setMeasureunitPriority(Integer measureunitPriority) {
        this.measureunitPriority = measureunitPriority;
    }

    public void setMeasureId(Integer measureId) {
        this.measureId = measureId;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    @Override
    public String toString() {
        return "UnitmeasureDTO{" +
                "unitmeasureId=" + unitmeasureId +
                ", unitmeasureName='" + unitmeasureName + '\'' +
                ", unitmeasureShortname='" + unitmeasureShortname + '\'' +
                ", measureunitId=" + measureunitId +
                ", measureunitPriority=" + measureunitPriority +
                ", measureId=" + measureId +
                ", measureName='" + measureName + '\'' +
                '}';
    }
}
