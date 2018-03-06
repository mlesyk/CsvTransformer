package org.mlesyk.gwt.csvdashboard.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.mlesyk.gwt.csvdashboard.shared.dto.AbstractRuleDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by Maks on 23.09.2017.
 */
@RemoteServiceRelativePath("upload")
public interface TableLoadingService extends RemoteService {
    String[][] getTableData(String fileName);

    String[][] getFilteredTableData(AbstractRuleDTO rule);

    String[][] removeRule(int ruleId);

    Map<Integer, List<Integer>> updateRemovalMap();
}
