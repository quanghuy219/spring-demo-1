package springmvc.demo.controllers;


import org.json.JSONObject;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springmvc.demo.models.Reservation;
import springmvc.demo.models.ResponseModel;
import springmvc.demo.services.ReservationService;
import springmvc.demo.utils.Commons;
import springmvc.demo.utils.Response;

import java.util.Map;

@RestController
@RequestMapping("api/reservation")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class ReservationsController {

    /**
     * API to make reservation
     * @param params: {name: client's name, from: booking from, to: booking to, room: room number}
     * @return ResponseEntity
     */

    @PostMapping(produces = {"application/hal+json"})
    public @ResponseBody
    ResponseEntity<String> createReservation(@RequestBody Map<String, String> params) {
        Reservation reservation = Commons.getReservationFromParams(params);

        if(reservation == null) {
            return new ResponseModel(JSONObject.NULL, "Invalid parameters", HttpStatus.BAD_REQUEST).toResponse();
        }

        return ReservationService.addNewReservation(reservation).toResponse();
    }
}