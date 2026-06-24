package finki.emt.lab_emt.model.projections;



//@JsonInclude(JsonInclude.Include.NON_NULL) // THis will make only non-null fields to be sent to the JSON of the instance
public interface UserProjection{

//    @JsonProperty("Name")
    String getName();

//    @JsonProperty("Surname")
    String getSurname();
}
