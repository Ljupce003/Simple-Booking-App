package finki.emt.lab_emt.web.controlers;

import finki.emt.lab_emt.config.DataInitializer;
import finki.emt.lab_emt.service.domain.HostService;
import finki.emt.lab_emt.service.domain.SmestuvanjeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class SwaggerControlController {

    private final DataInitializer initializer;
    private final SmestuvanjeService smestuvanjeService;
    private final HostService hostService;

    public SwaggerControlController(DataInitializer initializer, SmestuvanjeService smestuvanjeService, HostService hostService) {
        this.initializer = initializer;
        this.smestuvanjeService = smestuvanjeService;
        this.hostService = hostService;
    }


    @GetMapping("/")
    public void redirectToSwagger(HttpServletResponse response) throws IOException {

        response.sendRedirect("/swagger-ui/index.html");
    }

    @Operation(summary = "Initialize sample data", description = "Populates the system with initial users, hosts, accommodations, etc.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Initialization completed successfully"),
            @ApiResponse(responseCode = "400", description = "Initialization failed or data already seeded")
    })
    @GetMapping("/run_initializer")
    public ResponseEntity<Boolean> initialize() {
        this.initializer.init();
        return ResponseEntity.ok(true);
    }


    @Operation(
            summary = "Manually update materialized and subselect views",
            description = "Triggers manual update of views related to hosts and smestuvanje"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Views successfully updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/update-views-manually")
    public ResponseEntity<Boolean> UpdateViews(){
        this.hostService.updateMaterializedView();
        this.smestuvanjeService.updateView();
        return ResponseEntity.ok(true);
    }
}
