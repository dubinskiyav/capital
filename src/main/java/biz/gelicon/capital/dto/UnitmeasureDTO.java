package biz.gelicon.capital.dto;

/**
 * Единицы измерения DTO - аналог выборки
 * SELECT UM.id,
 *        UM.name,
 *        UM.short_name,
 *        MU.id measureunitId,
 *        MU.priority,
 *        M.id measureId,
 *        M.name measureName
 * FROM   unitmeasure UM,
 *        measureunit MU,
 *        measure M
 * WHERE  MU.unitmeasure_id = UM.id
 *   AND  M.id = MU.measure_id
 * ORDER BY UM.id
 */
public class UnitmeasureDTO {

    private Integer id;

    private String name;

    private String shortName;

    private Integer measureunitId;

    private Integer priority;

    private Integer measureId;

    private String measureName;

    public UnitmeasureDTO(
            Integer id,
            String name,
            String shortName,
            Integer measureunitId,
            Integer priority,
            Integer measureId,
            String measureName
    ) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.measureunitId = measureunitId;
        this.priority = priority;
        this.measureId = measureId;
        this.measureName = measureName;
    }

    public UnitmeasureDTO() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public Integer getMeasureunitId() {
        return measureunitId;
    }

    public Integer getPriority() {
        return priority;
    }

    public Integer getMeasureId() {
        return measureId;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setMeasureunitId(Integer measureunitId) {
        this.measureunitId = measureunitId;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", measureunitId=" + measureunitId +
                ", priority=" + priority +
                ", measureId=" + measureId +
                ", measureName='" + measureName + '\'' +
                '}';
    }
}
