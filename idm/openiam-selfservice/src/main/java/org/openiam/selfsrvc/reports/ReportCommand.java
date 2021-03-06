package org.openiam.selfsrvc.reports;

import java.io.File;
import java.util.Collections;
import java.util.List;
import org.openiam.idm.srvc.report.dto.ReportSubscriptionDto;

public class ReportCommand {
    private List<ReportSubscriptionDto> reports = Collections.EMPTY_LIST;
    private ReportSubscriptionDto report = new ReportSubscriptionDto();
    private File[] files;
    
    public List<ReportSubscriptionDto> getReports() {
        return reports;
    }

    public void setRepors(List<ReportSubscriptionDto> reports) {
        this.reports = reports;
    }

    public ReportSubscriptionDto getReport() {
        return report;
    }

    public void setReport(ReportSubscriptionDto report) {
        this.report = report;
    }

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}
}
