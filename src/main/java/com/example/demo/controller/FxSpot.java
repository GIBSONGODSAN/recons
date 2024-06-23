// package com.example.demo.controller;

// import org.springframework.web.bind.annotation.CrossOrigin;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.demo.model.SpotBanks;
// import com.example.demo.service.DataStreamService;
// import com.example.demo.bankSpotRates.SpotRateAnalysis;
// import com.example.demo.bankSpotRates.SpotRateService;
// import com.example.demo.bankSpotRates.SpotRateService.SpotValues;
// import com.example.demo.handler.ResponseHandler;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;


// @CrossOrigin(value = "http://localhost:3000")
// @RestController
// @RequestMapping("/fxspot")
// public class FxSpot {

//     @Autowired
//     DataStreamService dataStreamService;

//     @Autowired
//     SpotRateService spotRateService;

//     @Autowired
//     SpotRateAnalysis spotRateAnalysis;

//     @PostMapping("/spotprice")
//     public ResponseEntity<Object> getSpotPrice(@RequestBody SpotBanks spotBanks) {
//         String pairType = spotBanks.getPairType();
//         String ccyPair = spotBanks.getCcyPair();
//         int lots = spotBanks.getLots();
//         Map<String, Object> currencyData = dataStreamService.getCurrencyData(pairType, ccyPair);

//         if (currencyData != null) {
//             double bid = (double) currencyData.get("bid");
//             double ask = (double) currencyData.get("ask");
//             Object spotValues = calculateSpotValues(bid, ask, lots);
//             return ResponseHandler.generateResponse("Spot values calculated", HttpStatus.OK, spotValues);
//         } else {
//             return ResponseEntity.status(404).body("Currency data not found");
//         }
//     }

//     public Object calculateSpotValues(double bid, double ask, int lots) {
//         Map<String, SpotValues> spotValues = spotRateService.getSpotValues(bid, ask, lots);
//         Map<String, Map<String, Object>> displayValues = SpotRateAnalysis.findBestBanks(spotValues);
//         Map<String, Object> combinedValues = new HashMap<>();
//         combinedValues.put("SpotValues", spotValues);
//         combinedValues.put("DisplayValues", displayValues);
//         return combinedValues;
//     }
// }
