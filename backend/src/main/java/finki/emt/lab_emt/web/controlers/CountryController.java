package finki.emt.lab_emt.web.controlers;

import finki.emt.lab_emt.model.dto.countryDTO.CreateCountryDTO;
import finki.emt.lab_emt.model.dto.countryDTO.DisplayCountryDTO;
import finki.emt.lab_emt.service.application.CountryApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/country")
@Tag(name = "Country API", description = "Endpoints for managing countries")
public class CountryController {

    private final CountryApplicationService countryService;

    public CountryController(CountryApplicationService countryService) {
        this.countryService = countryService;
    }






    @Operation(summary = "Get all countries", description = "Returns a list of all available countries")
    @ApiResponse(responseCode = "200", description = "List of countries retrieved successfully")
    @GetMapping
    public List<DisplayCountryDTO> findAll() {
        return this.countryService.findAll();
    }






    @Operation(summary = "Find country by ID", description = "Returns a specific country by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Country found"),
            @ApiResponse(responseCode = "404", description = "Country not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DisplayCountryDTO> findById(@PathVariable Long id) {
        return this.countryService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }






    @Operation(summary = "Create a new country", description = "Adds a new country to the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Country created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid country data")
    })
    @PostMapping("/add")
    public ResponseEntity<DisplayCountryDTO> save(@RequestBody CreateCountryDTO countryDto) {
        return this.countryService.save(countryDto)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.badRequest()::build);
    }






    @Operation(summary = "Update country", description = "Updates the country with the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Country updated successfully"),
            @ApiResponse(responseCode = "400", description = "Update failed due to invalid data")
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayCountryDTO> update(@PathVariable Long id, @RequestBody CreateCountryDTO countryDto) {
        return this.countryService.update(id, countryDto)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.badRequest()::build);
    }






    @Operation(summary = "Delete country", description = "Deletes the country with the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Country deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Country not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        if (this.countryService.findById(id).isPresent()) {
            this.countryService.deleteById(id);
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.notFound().build();
    }
}
