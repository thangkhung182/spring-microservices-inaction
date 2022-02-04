package com.trungnguyen.license.service;

import com.trungnguyen.license.config.ServiceConfig;
import com.trungnguyen.license.model.License;
import com.trungnguyen.license.repository.LicenseRepository;
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

    public License getLicense(String licenseId, String organizationId) {
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
}
