package biz.gelicon.capital.dto;

/**
 * Единицы измерения DTO - аналог выборки
 * SELECT UM.unitmeasure_id,
 *        UM.unitmeasure_name,
 *        UM.unitmeasure_shortname,
 *        MU.measureunit_id,
 *        MU.measureunit_priority,
 *        M.measure_id,
 *        M.measure_name
 * FROM   unitmeasure UM,
 *        measureunit MU,
 *        measure M
 * WHERE  MU.unitmeasure_id = UM.unitmeasure_id
 *   AND  M.measure_id = MU.measure_id
 * ORDER BY UM.unitmeasure_id
 */
public class UnitmeasureDTO {

    private Integer unitmeasureId;

    private String unitmeasureName;

    private String unitmeasureShortname;

    private Integer measureunitId;

    private Integer measureunitPriority;

    private Integer measureId;

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
