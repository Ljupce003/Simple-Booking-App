package finki.emt.lab_emt.web.controlers;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.DisplaySmestuvanjeWithRelationDTO;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.SmestuvanjeStatsDTO;
import finki.emt.lab_emt.model.enums.CategorySmestuvanje;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.CreateSmestuvanjeDTO;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.DisplaySmestuvanjeDTO;
import finki.emt.lab_emt.model.views.SmestuvanjeByHostView;
import finki.emt.lab_emt.service.application.SmestuvanjeApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/smestuvanje")
@Tag(name = "Smestuvanje API", description = "Endpoints for managing accommodations and reservations")
public class SmestuvanjeController {

    private final SmestuvanjeApplicationService smestuvanjeService;

    public SmestuvanjeController(SmestuvanjeApplicationService smestuvanjeService) {
        this.smestuvanjeService = smestuvanjeService;
    }

    @Operation(summary = "Get all accommodations", description = "Returns a list of all available accommodations")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public List<DisplaySmestuvanjeDTO> findAll() {
        return this.smestuvanjeService.findAll();
    }


    @GetMapping("/paginated")
    public ResponseEntity<Page<DisplaySmestuvanjeDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(smestuvanjeService.findAll(pageable));
    }


    @Operation(summary = "Find accommodation by ID", description = "Returns details for a specific accommodation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accommodation found"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DisplaySmestuvanjeDTO> findById(@PathVariable Long id) {
        return this.smestuvanjeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @Operation(summary = "Create new accommodation", description = "Adds a new accommodation based on provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accommodation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/add")
    public ResponseEntity<DisplaySmestuvanjeDTO> save(@RequestBody CreateSmestuvanjeDTO smestuvanjeDto) {
        return this.smestuvanjeService.save(smestuvanjeDto)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.badRequest()::build);
    }

    @Operation(summary = "Filter accommodations", description = "Filters accommodations based on search criteria")
    @ApiResponse(responseCode = "200", description = "Filtered list returned")
    @PostMapping("/filter")
    public List<DisplaySmestuvanjeDTO> filter(@RequestBody CreateSmestuvanjeDTO smestuvanjeDto) {
        return this.smestuvanjeService.filter(smestuvanjeDto);
    }

    @Operation(summary = "Edit accommodation", description = "Updates an existing accommodation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accommodation updated"),
            @ApiResponse(responseCode = "400", description = "Update failed")
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplaySmestuvanjeDTO> update(
            @PathVariable Long id,
            @RequestBody CreateSmestuvanjeDTO smestuvanjeDto) {
        return this.smestuvanjeService.update(id, smestuvanjeDto)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.badRequest()::build);
    }

    @Operation(summary = "Rent accommodation", description = "Marks an accommodation as rented by the current user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accommodation rented successfully"),
            @ApiResponse(responseCode = "400", description = "Unable to rent accommodation")
    })
    @PostMapping("/rent/{id}")
    public ResponseEntity<DisplaySmestuvanjeDTO> rent(
            @PathVariable Long id,
            HttpServletRequest request) {
        return this.smestuvanjeService.rent(id, request.getRemoteUser())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.badRequest()::build);
    }

    @Operation(summary = "Return rented accommodation", description = "Ends the rental of an accommodation by the current user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accommodation returned"),
            @ApiResponse(responseCode = "400", description = "Unable to return accommodation")
    })
    @DeleteMapping("/DeleteRent/{id}")
    public ResponseEntity<DisplaySmestuvanjeDTO> rentOut(
            @PathVariable Long id,
            HttpServletRequest request) {
        return this.smestuvanjeService.rentOut(id, request.getRemoteUser())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.badRequest()::build);
    }

    @Operation(summary = "Get rented accommodations", description = "Returns accommodations currently rented by the user")
    @ApiResponse(responseCode = "200", description = "List of rented accommodations returned")
    @GetMapping("/rented")
    public List<DisplaySmestuvanjeDTO> findRented(HttpServletRequest request) {
        return this.smestuvanjeService.findRented(request.getRemoteUser());
    }

    @Operation(summary = "Delete accommodation", description = "Deletes a specific accommodation by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accommodation deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Accommodation not found or could not be deleted")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        if (this.smestuvanjeService.findById(id).isPresent()) {
            this.smestuvanjeService.deleteById(id);
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategorySmestuvanje>> getCategories(){
        return ResponseEntity.ok(CategorySmestuvanje.getValues());
    }

    @Operation(summary = "Reserve accommodation", description = "Adds a reservation for the given accommodation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation successful"),
            @ApiResponse(responseCode = "400", description = "Unable to reserve accommodation")
    })

    @PostMapping("/reserve/{id}")
    public ResponseEntity<Boolean> reserve(
            @PathVariable Long id, @AuthenticationPrincipal AgencyUser user) {
        System.out.println(user.getUsername());
        return this.smestuvanjeService.addReservation(id,user)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.badRequest()::build);
    }

    @Operation(
            summary = "Get accommodation counts by host",
            description = "Returns a list of hosts with the count of accommodations they manage, based on the SQL view"
    )
    @ApiResponse(responseCode = "200", description = "List of accommodations per host retrieved successfully")
    @GetMapping("/by-host")
    public ResponseEntity<List<SmestuvanjeByHostView>> getSmestuvanjeCountByHost() {
        return ResponseEntity.ok(this.smestuvanjeService.getAllViews());
    }

//    @Operation(summary = "Get accommodation count for a specific host",
//            description = "Returns the number of accommodations for the given host ID, based on the SQL view"
//    )
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Accommodation count for host retrieved successfully"),
//            @ApiResponse(responseCode = "400", description = "Invalid host ID or no data available")
//    })
//    @GetMapping("/by-host/{id}")
//    public ResponseEntity<SmestuvanjeByHostView> getSmestuvanjeCountByHost(
//            @PathVariable Long id) {
//        return this.smestuvanjeService.getViewByHost(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(ResponseEntity.badRequest()::build);
//    }

    @Operation(summary = "Get accommodation statistics by category", description = "Returns statistics (average price, etc.) grouped by accommodation category using HASHMAP")
    @ApiResponse(responseCode = "200", description = "Category statistics retrieved successfully")
    @GetMapping("/statistics")
    public ResponseEntity<Set<Map.Entry<CategorySmestuvanje,Double>>> statistics() {
        return ResponseEntity.ok(this.smestuvanjeService.statistics());
    }

    @Operation(summary = "Get detailed accommodation statistics", description = "Returns a list of statistical data points (e.g., counts, averages) for accommodations")
    @ApiResponse(responseCode = "200", description = "Detailed statistics retrieved successfully")
    @GetMapping("/stats")
    public ResponseEntity<List<SmestuvanjeStatsDTO>> stats() {
        return ResponseEntity.ok(this.smestuvanjeService.stats());
    }


    @GetMapping("/fetch-by-id/{id}")
    public ResponseEntity<DisplaySmestuvanjeWithRelationDTO> fetchById(@PathVariable Long id) {
        if(id==null) return ResponseEntity.badRequest().build();

        return this.smestuvanjeService.fetch_w_relations_by_id(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}



