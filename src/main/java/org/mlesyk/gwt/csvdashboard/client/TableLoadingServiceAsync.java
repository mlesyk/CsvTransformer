package org.mlesyk.gwt.csvdashboard.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.mlesyk.gwt.csvdashboard.shared.dto.AbstractRuleDTO;

import java.util.List;
import java.util.Map;


public interface TableLoadingServiceAsync {
    void getTableData(String fileName, AsyncCallback<String[][]> async);

    void getFilteredTableData(AbstractRuleDTO rule, AsyncCallback<String[][]> async);

    void removeRule(int ruleId, AsyncCallback<String[][]> async);

    void updateRemovalMap(AsyncCallback<Map<Integer, List<Integer>>> async);

}
