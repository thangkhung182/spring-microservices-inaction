package com.trungnguyen.license.controller;

import com.trungnguyen.license.model.License;
import com.trungnguyen.license.service.LicenseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
@Tag(name = "License")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value="/{licenseId}/{clientType}",method = RequestMethod.GET)
    public ResponseEntity<License> getLicense( @PathVariable("organizationId") String organizationId,
                                               @PathVariable("licenseId") String licenseId,
                                               @PathVariable("clientType") String clientType) {

        License license = licenseService.getLicense(licenseId, organizationId, clientType);
//        license.add(
//                linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId())).withSelfRel(),
//                linkTo(methodOn(LicenseController.class).createLicense(license)).withRel("createLicense"),
//                linkTo(methodOn(LicenseController.class).updateLicense(license)).withRel("updateLicense"),
//                linkTo(methodOn(LicenseController.class).deleteLicense(license.getLicenseId())).withRel("deleteLicense")
//        );

        return ResponseEntity.ok(license);
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody License request) {
        return ResponseEntity.ok(licenseService.updateLicense(request));
    }

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License request) {
        return ResponseEntity.ok(licenseService.createLicense(request));
    }

    @DeleteMapping(value="/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
    }
}
