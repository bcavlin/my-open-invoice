/*
 * Copyright 2017 Branislav Cavlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bgh.myopeninvoice.jsfbeans;

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.*;
import com.bgh.myopeninvoice.jsfbeans.model.InvoiceEntityLazyModel;
import com.bgh.myopeninvoice.reporting.BIRTReport;
import com.bgh.myopeninvoice.reporting.ReportRunner;
import com.bgh.myopeninvoice.utils.FacesUtils;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.joda.time.*;
import org.omnifaces.util.Faces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by bcavlin on 17/03/17.
 */
@ManagedBean
@ViewScoped
@Component
public class InvoiceBean implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(InvoiceBean.class);

    private InvoiceDAO invoiceDAO;

    //Invoice data
    private LazyDataModel<InvoiceEntity> invoiceEntityLazyDataModel;

    private Collection<CompanyContactEntity> companyContactEntityCollectionForSelection;

    private Collection<CompaniesEntity> companiesEntityCollectionForSelection;

    private Collection<TaxEntity> taxEntityCollectionForSelection;

    private InvoiceEntity selectedInvoiceEntity;

    private InvoiceItemsEntity selectedInvoiceItemsEntity;

    private Date dateFromTimesheet;

    private Date dateToTimesheet;

    private int pageSize = 20;

    private ReportRunner reportRunner;

    private Collection<ReportsEntity> reportsEntityCollection;

    private AttachmentEntity selectedAttachmentEntity;

    @Autowired
    public InvoiceBean(InvoiceDAO invoiceDAO, ReportRunner reportRunner) {
        this.invoiceDAO = invoiceDAO;
        this.reportRunner = reportRunner;
    }

    @PostConstruct
    private void init() {
        logger.info("Initializing entries");
        invoiceEntityLazyDataModel = new InvoiceEntityLazyModel(invoiceDAO);
        Predicate p = QCompanyContactEntity.companyContactEntity.companiesByCompanyId.ownedByMe.isTrue();
        companyContactEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompanyContactRepository().findAll(p));
        Predicate p1 = QCompaniesEntity.companiesEntity.ownedByMe.isFalse();
        companiesEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompaniesRepository().findAll(p1));

        taxEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getTaxRepository().findAll());

        //first in the previous month
        dateFromTimesheet = new DateTime().minusDays(15).dayOfMonth().withMinimumValue().toDate();
        //last day of the month
        dateToTimesheet = new DateTime().plusDays(15).dayOfMonth().withMaximumValue().toDate();

//        reportTemplatesEntity = invoiceDAO.getReportTemplatesRepository().findOne(QReportTemplatesEntity.reportTemplatesEntity.templateName.eq(DEFAULT_INVOICE));

        selectReports();
    }

    private void selectReports() {
        Predicate predicate = null;

        if (selectedInvoiceEntity != null) {
            predicate = QReportsEntity.reportsEntity.invoiceByInvoiceId.invoiceId.eq(selectedInvoiceEntity.getInvoiceId());
        }

        reportsEntityCollection = Lists.newArrayList(invoiceDAO.getReportsRepository().findAll(predicate, new Sort(Sort.Direction.DESC, "dateCreated")));
    }

    private void refresh() {
        logger.info("Loading entries");
        invoiceEntityLazyDataModel = new InvoiceEntityLazyModel(invoiceDAO);
        if(selectedInvoiceEntity!=null) {
            selectedInvoiceEntity = invoiceDAO.getInvoiceRepository().findOne(selectedInvoiceEntity.getInvoiceId());
        }
    }

    public void addNewInvoiceListener(ActionEvent event) {
        selectedInvoiceEntity = new InvoiceEntity();
        selectedInvoiceEntity.setCreatedDate(new DateTime().toDate());
        selectedInvoiceEntity.setFromDate(new LocalDate().withDayOfMonth(1).toDate());
        selectedInvoiceEntity.setToDate(new LocalDate().dayOfMonth().withMaximumValue().toDate());
        selectedInvoiceEntity.setDueDate(new LocalDate().plusMonths(1).withDayOfMonth(15).toDate());
        selectedInvoiceEntity.setInvoiceItemsByInvoiceId(new ArrayList<>()); //for value calculation

        selectedInvoiceItemsEntity = null;
    }

    public void addNewInvoiceItemsListener(ActionEvent event) {
        selectedInvoiceItemsEntity = new InvoiceItemsEntity();
        selectedInvoiceItemsEntity.setInvoiceId(selectedInvoiceEntity.getInvoiceId());
        selectedInvoiceItemsEntity.setQuantity(new BigDecimal(0));
    }

    public void addNewTimesheetListener(ActionEvent event) {
        //first in the previous month
        dateFromTimesheet = new DateTime().minusDays(15).dayOfMonth().withMinimumValue().toDate();
        //last day of the month
        dateToTimesheet = new DateTime().plusDays(15).dayOfMonth().withMaximumValue().toDate();
        //refresh
        selectedInvoiceItemsEntity = invoiceDAO.getInvoiceItemsRepository().findOne(selectedInvoiceItemsEntity.getInvoiceItemId());

        selectedInvoiceItemsEntity.getTimeSheetsByInvoiceItemId()
                .stream().min((o1, o2) -> o1.getItemDate().compareTo(o2.getItemDate()))
                .ifPresent(timeSheetEntity -> dateFromTimesheet = timeSheetEntity.getItemDate().compareTo(dateFromTimesheet) > 0 ? dateFromTimesheet : timeSheetEntity.getItemDate());

        selectedInvoiceItemsEntity.getTimeSheetsByInvoiceItemId()
                .stream().max((o1, o2) -> o1.getItemDate().compareTo(o2.getItemDate()))
                .ifPresent(timeSheetEntity -> dateToTimesheet = timeSheetEntity.getItemDate().compareTo(dateToTimesheet) < 0 ? dateToTimesheet : timeSheetEntity.getItemDate());
    }

    public void addNewAttachmentListener(ActionEvent event) {

    }

    public void ajaxChangeRowInvoiceListener() {
        selectedInvoiceItemsEntity = null;
        selectReports();
    }

    public void ajaxChangeRowInvoiceItemListener() {
    }

    public void addOrEditInvoiceListener(ActionEvent event) {
        if (selectedInvoiceEntity != null && selectedInvoiceEntity.getTitle() != null) {
            RequestContext.getCurrentInstance().execute("PF('invoice-form-dialog').hide()");

            logger.info("Adding/editing entity {}", selectedInvoiceEntity.toString());
            selectedInvoiceEntity = invoiceDAO.getInvoiceRepository().save(selectedInvoiceEntity);
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected entity is null or title not set");
        }
    }

    public void addOrEditInvoiceAttachmentListener(ActionEvent event) {
        if (selectedInvoiceEntity != null) {
            RequestContext.getCurrentInstance().execute("PF('invoice-attachment-form-dialog').hide()");

            logger.info("Adding/editing attachment");
            invoiceDAO.getAttachmentRepository().save(selectedInvoiceEntity.getAttachmentsByInvoiceId());
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected entity is null");
        }
    }

    public void addOrEditInvoiceItemsListener(ActionEvent event) {
        if (selectedInvoiceEntity != null && selectedInvoiceItemsEntity != null) {
            RequestContext.getCurrentInstance().execute("PF('invoice-items-form-dialog').hide()");

            logger.info("Adding/editing entity {} for {}", selectedInvoiceEntity.toString(), selectedInvoiceItemsEntity.toString());

            //update total
            if (selectedInvoiceItemsEntity.getTimeSheetTotal().compareTo(new BigDecimal(0)) > 0) {
                selectedInvoiceItemsEntity.setQuantity(selectedInvoiceItemsEntity.getTimeSheetTotal());
            }

            selectedInvoiceItemsEntity = invoiceDAO.getInvoiceItemsRepository().save(selectedInvoiceItemsEntity);
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected entity is null or title not set");
        }
    }

    public void addOrEditTimesheetListener(ActionEvent event) {
        if (selectedInvoiceEntity != null && selectedInvoiceItemsEntity != null) {
            RequestContext.getCurrentInstance().execute("PF('invoice-items-timesheet-form-dialog').hide()");

            logger.info("Adding/editing entity {} for {}", selectedInvoiceEntity.toString(), selectedInvoiceItemsEntity.toString());

            //filter out null or 0.00 values
            selectedInvoiceItemsEntity.getTimeSheetsByInvoiceItemId().removeIf(o -> o.getHoursWorked() == null || o.getHoursWorked().equals(new BigDecimal(0.0)));
            //update total
            if (selectedInvoiceItemsEntity.getTimeSheetTotal().compareTo(new BigDecimal(0)) > 0) {
                selectedInvoiceItemsEntity.setQuantity(selectedInvoiceItemsEntity.getTimeSheetTotal());
            }

            invoiceDAO.getInvoiceItemsRepository().save(selectedInvoiceItemsEntity);
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected entity is null or title not set");
        }
    }

    public void deleteInvoiceListener(ActionEvent event) {
        if (selectedInvoiceEntity != null) {
            logger.info("Deleting entity {}", selectedInvoiceEntity.toString());
            invoiceDAO.getInvoiceRepository().delete(selectedInvoiceEntity.getInvoiceId());
            refresh();
            FacesUtils.addSuccessMessage("Entity deleted");
            selectedInvoiceEntity = null;
        } else {
            FacesUtils.addErrorMessage("Selected entity is null");
        }
    }

    public void deleteInvoiceItemsListener(ActionEvent event) {
        if (selectedInvoiceEntity != null && selectedInvoiceItemsEntity != null) {
            logger.info("Deleting entity [{}] item [{}]", selectedInvoiceEntity.toString(), selectedInvoiceItemsEntity.toString());
            invoiceDAO.getInvoiceItemsRepository().delete(selectedInvoiceItemsEntity.getInvoiceItemId());
            refresh();
            FacesUtils.addSuccessMessage("Entity deleted");
            selectedInvoiceItemsEntity = null;
        } else {
            FacesUtils.addErrorMessage("Selected entity is null");
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        logger.info("Uploading {}", event.getFile().getFileName());
        if (selectedInvoiceEntity != null) {
            Collection<AttachmentEntity> attachmentsByInvoiceId = selectedInvoiceEntity.getAttachmentsByInvoiceId();
            if (attachmentsByInvoiceId == null) {
                attachmentsByInvoiceId = new ArrayList<>();
                selectedInvoiceEntity.setAttachmentsByInvoiceId(attachmentsByInvoiceId);
            }

            AttachmentEntity attachmentEntity = new AttachmentEntity();
            attachmentEntity.setInvoiceId(selectedInvoiceEntity.getInvoiceId());
            attachmentEntity.setInvoiceByInvoiceId(selectedInvoiceEntity);
            attachmentEntity.setContent(event.getFile().getContents());
            attachmentEntity.setFilename(event.getFile().getFileName());

            attachmentsByInvoiceId.add(attachmentEntity);
        }
    }

    public void deleteAttachment(AttachmentEntity attachmentEntity) {
        logger.info("Deleting attachment [{}]", attachmentEntity.toString());
        invoiceDAO.getAttachmentRepository().delete(attachmentEntity.getAttachmentId());
        refresh();
    }

    public void deleteReport(ReportsEntity reportsEntity) {
        logger.info("Deleting reportsEntity [{}]", reportsEntity.toString());
        invoiceDAO.getReportsRepository().delete(reportsEntity.getReportId());
        refresh();
        selectReports();
    }

    public void download(AttachmentEntity attachmentEntity) throws IOException {
        Faces.sendFile(attachmentEntity.getContent(), attachmentEntity.getFilename(), true);
    }

    public void download(ReportsEntity reportsEntity) throws IOException {
        Faces.sendFile(reportsEntity.getContent(), reportsEntity.getReportName(), true);
    }

    public void updateSelectionFromTo() {
        if (selectedInvoiceEntity != null
                && selectedInvoiceEntity.getCompanyContactFrom() != null
                && selectedInvoiceEntity.getCompanyTo() != null
                ) {
            if (selectedInvoiceEntity.getCompanyContactByCompanyContactFrom() == null || selectedInvoiceEntity.getCompanyContactByCompanyContactFrom().getCompanyContactId() != selectedInvoiceEntity.getCompanyContactFrom()) {
                selectedInvoiceEntity.setCompanyContactByCompanyContactFrom(invoiceDAO.getCompanyContactRepository().findOne(selectedInvoiceEntity.getCompanyContactFrom()));
                selectedInvoiceEntity.setCompaniesByCompanyTo(invoiceDAO.getCompaniesRepository().findOne(selectedInvoiceEntity.getCompanyTo()));
                final ContractsEntity validContract = selectedInvoiceEntity.getCompanyContactByCompanyContactFrom().findValidContract(selectedInvoiceEntity.getCompaniesByCompanyTo());
                if (validContract != null) {
                    selectedInvoiceEntity.setRate(validContract.getRate());
                    selectedInvoiceEntity.setRateUnit(validContract.getRateUnit());
                    selectedInvoiceEntity.setCcyId(validContract.getCcyId());
                    selectedInvoiceEntity.setCurrencyByCcyId(validContract.getCurrencyByCcyId());

                    if (StringUtils.isBlank(selectedInvoiceEntity.getTitle())) {
                        selectedInvoiceEntity.setTitle(selectedInvoiceEntity.getCompanyContactByCompanyContactFrom().getCompaniesByCompanyId().getShortName() +
                                "-" + LocalDate.now().getYear() + "-" + invoiceDAO.getInvoiceCounterSeq());
                    }
                }
            }
        }
    }

    public String getImageAttachment(AttachmentEntity attachmentEntity) throws IOException, ImageReadException {
        if (attachmentEntity != null && attachmentEntity.getContent().length > 0) {
            if (attachmentEntity.getLoadProxy()) {
                return "/images/" + attachmentEntity.getFileExtension() + ".png";
            } else {

                selectedAttachmentEntity = attachmentEntity;

                ImageFormat mimeType = Sanselan.guessFormat(attachmentEntity.getContent());
                if (mimeType != null && !"UNKNOWN".equalsIgnoreCase(mimeType.name)) {
                    return "data:image/" + mimeType.extension.toLowerCase() + ";base64," +
                            Base64.encodeBase64String(attachmentEntity.getContent());

                } else if (attachmentEntity.getImageData() != null && attachmentEntity.getImageData().length > 0) {
                    return "data:image/png;base64," +
                            Base64.encodeBase64String(attachmentEntity.getImageData());

                } else if ("pdf".equalsIgnoreCase(attachmentEntity.getFileExtension())) {
                    ByteArrayOutputStream baos = null;
                    PDDocument document = null;
                    try {
                        document = PDDocument.load(attachmentEntity.getContent());
                        final PDPage page = document.getPage(0);
                        PDFRenderer pdfRenderer = new PDFRenderer(document);
                        final BufferedImage bufferedImage = pdfRenderer.renderImage(0);
                        baos = new ByteArrayOutputStream();
                        ImageIO.write(bufferedImage, "png", baos);
                        baos.flush();
                        attachmentEntity.setImageData(baos.toByteArray());
                    } finally {
                        if (document != null) {
                            document.close();
                        }
                        if (baos != null) {
                            baos.close();
                        }
                    }
                    return "data:image/png;base64," +
                            Base64.encodeBase64String(attachmentEntity.getImageData());
                } else {
                    return null;
                }
            }
        } else if (selectedAttachmentEntity != null && selectedAttachmentEntity.getImageData() != null && selectedAttachmentEntity.getImageData().length > 0) {
            return "data:image/png;base64," +
                    Base64.encodeBase64String(selectedAttachmentEntity.getImageData());

        } else {
            return null;
        }
    }

    public String getReportData(ReportsEntity reportsEntity) throws IOException, ImageReadException {
        if (reportsEntity != null && reportsEntity.getContent().length > 0) {
            if (reportsEntity.getLoadProxy()) {
                return "/images/pdf.png";
            }
        }

        return null;
    }

    public String onFlowProcessTimesheet(FlowEvent event) {

        RequestContext.getCurrentInstance().execute("PF('invoice-items-timesheet-form-dialog').initPosition();");

        if ("select-date".equalsIgnoreCase(event.getOldStep()) && selectedInvoiceItemsEntity != null) {
            LocalDate startDate = new LocalDate(dateFromTimesheet).withDayOfWeek(DateTimeConstants.MONDAY);
            LocalDate endDate = new LocalDate(dateToTimesheet).withDayOfWeek(DateTimeConstants.SUNDAY);

            //need to reset in case we changed something
            selectedInvoiceItemsEntity = invoiceDAO.getInvoiceItemsRepository().findOne(selectedInvoiceItemsEntity.getInvoiceItemId());
            final List<TimeSheetEntity> timeSheetsByInvoiceItemId = (List<TimeSheetEntity>) selectedInvoiceItemsEntity.getTimeSheetsByInvoiceItemId();

            int days = Days.daysBetween(startDate, endDate).getDays() + 1;
            for (int i = 0; i < days; i++) {

                LocalDate d = startDate.withFieldAdded(DurationFieldType.days(), i);
                TimeSheetEntity e = new TimeSheetEntity();
                e.setInvoiceItemId(selectedInvoiceItemsEntity.getInvoiceItemId());
                e.setInvoiceItemsByInvoiceItemId(selectedInvoiceItemsEntity);
                e.setItemDate(d.toDate());

                final boolean anyMatch = timeSheetsByInvoiceItemId.stream().anyMatch(p -> p.getItemDate().equals(d.toDate()));
                if (!anyMatch) {
                    //TODO we cannot just add dates, we have to insert values
                    timeSheetsByInvoiceItemId.add(e);
                }
            }
            timeSheetsByInvoiceItemId.sort((l1, l2) -> l1.getItemDate().compareTo(l2.getItemDate()));

        }
        return event.getNewStep();
    }

    public void switchProxy(AttachmentEntity attachmentEntity) {
        attachmentEntity.setLoadProxy(!attachmentEntity.getLoadProxy());
    }

    public void runReportListener(ActionEvent event) throws IOException {

        if (selectedInvoiceEntity != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("p_invoice_id", selectedInvoiceEntity.getInvoiceId());

            Date printDate = new DateTime().toDate();

            String name = "INVOICE-" + selectedInvoiceEntity.getTitle() + "-" + new SimpleDateFormat("yyyyMMddHHmmss").format(printDate);

            final byte[] bytes = IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream("new_report_1.rptdesign"));

            final BIRTReport myReport = new BIRTReport(name, params, bytes, reportRunner);
            final ByteArrayOutputStream reportContent = myReport.runReport().getReportContent();

            ReportsEntity report = new ReportsEntity();
            report.setDateCreated(printDate);
            report.setInvoiceByInvoiceId(selectedInvoiceEntity);
            report.setContent(reportContent.toByteArray());
            report.setReportName(name + ".pdf");
            invoiceDAO.getReportsRepository().save(report);

            Faces.sendFile(reportContent.toByteArray(), name + ".pdf", true);

            FacesUtils.addSuccessMessage("Document has been generated");

        } else {
            FacesUtils.addErrorMessage("Selected invoice is null");

        }

    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public LazyDataModel<InvoiceEntity> getInvoiceEntityLazyDataModel() {
        return invoiceEntityLazyDataModel;
    }

    public void setInvoiceEntityLazyDataModel(LazyDataModel<InvoiceEntity> invoiceEntityLazyDataModel) {
        this.invoiceEntityLazyDataModel = invoiceEntityLazyDataModel;
    }

    public InvoiceEntity getSelectedInvoiceEntity() {
        return selectedInvoiceEntity;
    }

    public void setSelectedInvoiceEntity(InvoiceEntity selectedInvoiceEntity) {
        this.selectedInvoiceEntity = selectedInvoiceEntity;
    }

    public Collection<CompanyContactEntity> getCompanyContactEntityCollectionForSelection() {
        return companyContactEntityCollectionForSelection;
    }

    public void setCompanyContactEntityCollectionForSelection(Collection<CompanyContactEntity> companyContactEntityCollectionForSelection) {
        this.companyContactEntityCollectionForSelection = companyContactEntityCollectionForSelection;
    }

    public Collection<CompaniesEntity> getCompaniesEntityCollectionForSelection() {
        return companiesEntityCollectionForSelection;
    }

    public void setCompaniesEntityCollectionForSelection(Collection<CompaniesEntity> companiesEntityCollectionForSelection) {
        this.companiesEntityCollectionForSelection = companiesEntityCollectionForSelection;
    }

    public Collection<TaxEntity> getTaxEntityCollectionForSelection() {
        return taxEntityCollectionForSelection;
    }

    public void setTaxEntityCollectionForSelection(Collection<TaxEntity> taxEntityCollectionForSelection) {
        this.taxEntityCollectionForSelection = taxEntityCollectionForSelection;
    }

    public InvoiceItemsEntity getSelectedInvoiceItemsEntity() {
        return selectedInvoiceItemsEntity;
    }

    public void setSelectedInvoiceItemsEntity(InvoiceItemsEntity selectedInvoiceItemsEntity) {
        this.selectedInvoiceItemsEntity = selectedInvoiceItemsEntity;
    }

    public Date getDateFromTimesheet() {
        return dateFromTimesheet;
    }

    public void setDateFromTimesheet(Date dateFromTimesheet) {
        this.dateFromTimesheet = dateFromTimesheet;
    }

    public Date getDateToTimesheet() {
        return dateToTimesheet;
    }

    public void setDateToTimesheet(Date dateToTimesheet) {
        this.dateToTimesheet = dateToTimesheet;
    }

    public Collection<ReportsEntity> getReportsEntityCollection() {
        return reportsEntityCollection;
    }

    public void setReportsEntityCollection(Collection<ReportsEntity> reportsEntityCollection) {
        this.reportsEntityCollection = reportsEntityCollection;
    }

    public AttachmentEntity getSelectedAttachmentEntity() {
        return selectedAttachmentEntity;
    }

    public void setSelectedAttachmentEntity(AttachmentEntity selectedAttachmentEntity) {
        this.selectedAttachmentEntity = selectedAttachmentEntity;
    }

    public void updateSelectedAttachment(AttachmentEntity attachmentEntity) {
        selectedAttachmentEntity = attachmentEntity;
        RequestContext.getCurrentInstance().update("main-form:content-image-2-dialog");
        RequestContext.getCurrentInstance().execute("PF('content-image-2-dialog').show()");
    }
}
