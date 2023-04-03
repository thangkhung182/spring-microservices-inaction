package com.trungnguyen.license.service;

import com.trungnguyen.license.config.ServiceConfig;
import com.trungnguyen.license.model.License;
import com.trungnguyen.license.repository.LicenseRepository;
import com.trungnguyen.license.service.client.OrganizationDiscoveryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final MessageSource messages;

    private final LicenseRepository licenseRepository;

    private final ServiceConfig serviceConfig;

    //private final OrganizationFeignClient organizationFeignClient;

   // private final OrganizationRestTemplateClient organizationRestClient;

    private final OrganizationDiscoveryClient organizationDiscoveryClient;

    public License getLicense(String licenseId, String organizationId, String clientType) {
        var license = licenseRepository
                .findByOrganizationIdAndLicenseId(organizationId, licenseId)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(messages.getMessage(
                                "license.search.error.message", null, null),
                        licenseId, organizationId))
                );
        return license.withComment(serviceConfig.getProperty());
    }

    public License createLicense(License license){
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(serviceConfig.getProperty());
    }

    public License updateLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(serviceConfig.getProperty());
    }

    public String deleteLicense(String licenseId) {
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        String responseMessage = String.format(messages.getMessage(
                "license.delete.message", null, null),licenseId);
        return responseMessage;

    }

//    private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
//        Organization organization = null;
//
//        switch (clientType) {
//            case "feign":
//               System.out.println("I am using the feign client");
//             organization = organizationFeignClient.getOrganization(organizationId);
//                break;
//            case "rest":
//                System.out.println("I am using the rest client");
//                organization = organizationRestClient.getOrganization(organizationId);
//                break;
//            case "discovery":
//                System.out.println("I am using the discovery client");
//                organization = organizationDiscoveryClient.getOrganization(organizationId);
//                break;
//            default:
//                organization = organizationRestClient.getOrganization(organizationId);
//                break;
//        }
//
//        return organization;
//    }
}
