package ru.centralhardware.asiec.inventory.Web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.centralhardware.asiec.inventory.Service.AttributeService;

@RestController
@Api(value = "attribute")
@RequestMapping("/attribute")
public class AttributeController {

    private final AttributeService service;

    public AttributeController(AttributeService service) {
        this.service = service;
    }

    @ApiOperation(
            value = "get pageable list of attributes",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get attribute", response = String.class, responseContainer = "List")
    })
    @GetMapping(path = "list")
    public ResponseEntity<?> getAttribute(@RequestParam int page, @RequestParam int pageSIze) {
        Pageable pageable = PageRequest.of(page - 1, pageSIze);
        return ResponseEntity.ok().body(service.getAttributesName(pageable));
    }

    @ApiOperation(
            value = "get pageable list of attributes",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get attribute", response = String.class, responseContainer = "List")
    })
    @GetMapping(path = "list-for-equipment")
    public ResponseEntity<?> getAttributeForEquipment(@RequestParam int equipmentId) {
        return ResponseEntity.ok().body(service.getAttributesForEquipment(equipmentId));
    }

}