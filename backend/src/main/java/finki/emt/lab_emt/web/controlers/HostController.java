package finki.emt.lab_emt.web.controlers;

import finki.emt.lab_emt.model.dto.hostDTO.CreateHostDTO;
import finki.emt.lab_emt.model.dto.hostDTO.DisplayHostDTO;
import finki.emt.lab_emt.model.views.HostByCountry;
import finki.emt.lab_emt.service.application.HostApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/hosts")
@Tag(name = "Host API", description = "Endpoints for managing hosts")
public class HostController {

    private final HostApplicationService hostService;

    public HostController(HostApplicationService hostService) {
        this.hostService = hostService;
    }

    @Operation(summary = "Get all hosts", description = "Returns a list of all registered hosts")
    @ApiResponse(responseCode = "200", description = "List of hosts retrieved successfully")
    @GetMapping
    public List<DisplayHostDTO> findAll() {
        return this.hostService.findAll();
    }

    @Operation(summary = "Find host by ID", description = "Returns the host with the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Host found"),
            @ApiResponse(responseCode = "404", description = "Host not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DisplayHostDTO> findById(@PathVariable Long id) {
        return this.hostService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @Operation(summary = "Create new host", description = "Adds a new host to the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Host created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid host data")
    })
    @PostMapping("/add")
    public ResponseEntity<DisplayHostDTO> save(@RequestBody CreateHostDTO hostDto) {
        return this.hostService.save(hostDto)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.badRequest()::build);
    }

    @Operation(summary = "Update host", description = "Updates an existing host by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Host updated successfully"),
            @ApiResponse(responseCode = "400", description = "Update failed due to invalid data")
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayHostDTO> update(
            @PathVariable Long id,
            @RequestBody CreateHostDTO hostDto) {
        return this.hostService.update(id, hostDto)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.badRequest()::build);
    }

    @Operation(summary = "Delete host", description = "Deletes a host by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Host deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Host not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        if (this.hostService.findById(id).isPresent()) {
            this.hostService.deleteById(id);
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Get number of hosts by country",
            description = "Returns a list of countries with the corresponding number of registered hosts, based on the SQL view"
    )
    @ApiResponse(responseCode = "200", description = "List of host counts by country retrieved successfully")
    @GetMapping("/by-country")
    public ResponseEntity<List<HostByCountry>> getNumHostsByCountry() {
        return ResponseEntity.ok(this.hostService.findAllViews());
    }

//    @Operation(
//            summary = "Get number of hosts for a specific country",
//            description = "Returns the number of registered hosts for the given country ID, based on the SQL view"
//    )
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Host count for country retrieved successfully"),
//            @ApiResponse(responseCode = "400", description = "Invalid country ID or no data available")
//    })
//    @GetMapping("/by-country/{id}")
//    public ResponseEntity<HostByCountry> getNumHostsByCountry(@PathVariable Long id) {
//        return this.hostService.findViewByCountry(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(ResponseEntity.badRequest()::build);
//    }
}

