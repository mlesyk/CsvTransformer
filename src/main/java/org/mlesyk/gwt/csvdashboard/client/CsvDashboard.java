package org.mlesyk.gwt.csvdashboard.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.mlesyk.gwt.csvdashboard.shared.MathUtilConstants;
import org.mlesyk.gwt.csvdashboard.shared.RegexUtilConstants;
import org.mlesyk.gwt.csvdashboard.shared.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CsvDashboard is a View part of CsvTransformer
 */
public class CsvDashboard implements EntryPoint {

    private TableLoadingServiceAsync tableLoadingService;

    // Csv Dashboard content
    private VerticalPanel uploadView = new VerticalPanel();
    private VerticalPanel tableView = new VerticalPanel();
    private Grid table = new Grid();
    private FormPanel uploadForm = new FormPanel();
    private FlowPanel flowPanel = new FlowPanel();

    // "Add rule" dialog box content
    private DialogBox addRuleDialogBox = new DialogBox();
    private ListBox rulesDropBox = new ListBox();
    private FlexTable ruleLayout = new FlexTable();
    private Button addRuleButton = new Button("Add rule");

    private Map<Integer, List<Integer>> rulesRemovalMap;

    private static final String UTF8_BOM = "\uFEFF";

    public void onModuleLoad() {
        this.createUploadForm();
        this.createTable();

        RootPanel.get().add(uploadForm);
        RootPanel.get().add(tableView);
    }

    private void createUploadForm() {
        // http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/user/client/ui/FileUpload.html
        uploadForm.setMethod(FormPanel.METHOD_POST);
        //The HTTP request is encoded in multipart format.
        uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
        uploadForm.getElement().setId("uploadForm");
        uploadForm.setAction("CsvFileUpload"); // servlet CsvFileUpload
        uploadForm.setWidget(uploadView);
        FileUpload fileUpload = new FileUpload();
        fileUpload.setName("uploader"); // necessary, exception without it
        uploadView.add(fileUpload);
        Label maxUpload = new Label();
        maxUpload.setText("Maximum upload file size: 1MB");
        uploadView.add(maxUpload);
        uploadView.add(new Button("Submit", new ClickHandler() {
            public void onClick(ClickEvent event) {
                uploadForm.submit();
            }
        }));
        // handle upload finish - build table
        uploadForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {
                fetchTableData(submitCompleteEvent.getResults());
            }
        });
    }

    private void createTable() {
//        HorizontalPanel rulesFlow = new HorizontalPanel();
        HorizontalPanel tablePanel = new HorizontalPanel();
        table.getElement().setId("csvTable");
        tablePanel.add(table);
//        rulesFlow.add(flowPanel);
        tableView.add(flowPanel);
        tableView.add(tablePanel);
        tableView.add(addRuleButton);
        addRuleButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                createAddRuleDialogBox();
            }
        });
        addRuleButton.setVisible(false);
        table.setVisible(false);
    }

    private void fetchTableData(String fileName) {
        // handle UTF-8 BOM in response from servlet
        if (fileName.startsWith(UTF8_BOM)) {
            fileName = fileName.substring(1);
        }
        fileName = fileName.replace("\n", "").replace("\r","");
        if(tableLoadingService == null) {
            tableLoadingService = GWT.create(TableLoadingService.class);
        }
        AsyncCallback<String[][]> callback = new AsyncCallback<String[][]>() {
            @Override
            public void onFailure(Throwable caught) {
                // TODO: Do something with errors.
            }

            @Override
            public void onSuccess(String[][] result) {
                populateTable(result);
            }
        };
        tableLoadingService.getTableData(fileName, callback);
    }

    public void populateTable(String[][] data) {
        if(data != null) {
            table.resize(data.length, data[0].length);
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    table.setHTML(i, j, data[i][j]);
                }
            }
        } else {
            table.resize(1,1);
            table.setHTML(0,0,"No match found");
        }
        table.setVisible(true);
        addRuleButton.setVisible(true);
        uploadForm.setVisible(false);
    }

    private void createAddRuleDialogBox() {
        addRuleDialogBox.setText("Add new rule");
        addRuleDialogBox.setAnimationEnabled(true);
        VerticalPanel dialogBoxVPanel = new VerticalPanel();
        HorizontalPanel ruleSelector = new HorizontalPanel();
        rulesDropBox.addItem(RuleNameConstants.CALCULATE);
        rulesDropBox.addItem(RuleNameConstants.CHANGE_COLUMN_POSITION);
        rulesDropBox.addItem(RuleNameConstants.CLONE);
        rulesDropBox.addItem(RuleNameConstants.DELETE);
        rulesDropBox.addItem(RuleNameConstants.MATH_FILTER);
        rulesDropBox.addItem(RuleNameConstants.MERGE);
        rulesDropBox.addItem(RuleNameConstants.REGEX);
        rulesDropBox.setSelectedIndex(0);
        rulesDropBox.setVisibleItemCount(1);
        ruleSelector.add(rulesDropBox);
        Button applyRuleButton = new Button("Apply Rule");
        ruleSelector.add(applyRuleButton);
        addRuleDialogBox.add(ruleSelector);
        Button closeButton = new Button("Close");
        closeButton.getElement().setId("closeButton");
        applyRuleButton.addClickHandler(new ApplyRuleButtonClickHandler());
        rulesDropBox.addChangeHandler(new RulesDropBoxChangeHandler());
        closeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                ruleLayout.clear();
                addRuleDialogBox.clear();
                rulesDropBox.clear();
                addRuleDialogBox.hide();
            }
        });
        // build default layout
        this.createCalculateRuleLayout();
        DecoratorPanel decPanel = new DecoratorPanel();
        decPanel.setWidget(ruleLayout);
        dialogBoxVPanel.add(ruleSelector);
        dialogBoxVPanel.add(decPanel);
        dialogBoxVPanel.add(closeButton);
        addRuleDialogBox.add(dialogBoxVPanel);
        addRuleDialogBox.show();

    }

    private void createRegexFilterRuleLayout() {
        formatRuleLayoutDialogBox();
        ruleLayout.setHTML(0, 0, "Enter Regex Filter Rule Criteria");
        ruleLayout.setHTML(1,0,"Select column");
        ListBox firstColumnSelector = new ListBox();
        for(int i = 0; i < table.getColumnCount(); i++) {
            firstColumnSelector.addItem(table.getHTML(0, i));
        }
        firstColumnSelector.setSelectedIndex(0);
        firstColumnSelector.setVisibleItemCount(1);
        ruleLayout.setWidget(1, 1, firstColumnSelector);
        ruleLayout.setHTML(2,0,"Choose criteria:");
        ListBox regexFilterCriteria = new ListBox();
        regexFilterCriteria.setVisibleItemCount(1);
        regexFilterCriteria.addItem("Contains", Integer.toString(RegexUtilConstants.CONTAINS));
        regexFilterCriteria.addItem("Begins with", Integer.toString(RegexUtilConstants.BEGINS_WITH));
        regexFilterCriteria.addItem("Ends with", Integer.toString(RegexUtilConstants.ENDS_WITH));
        regexFilterCriteria.addItem("Equals", Integer.toString(RegexUtilConstants.EQUALS));
        regexFilterCriteria.addItem("Custom Regex Rule", Integer.toString(RegexUtilConstants.REGEX));
        regexFilterCriteria.setSelectedIndex(0);
        ruleLayout.setWidget(2, 1, regexFilterCriteria);
        ruleLayout.setHTML(3,0,"Enter filter data: ");
        ruleLayout.setWidget(3,1, new TextBox());
        regexFilterCriteria.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                if(regexFilterCriteria.getSelectedValue().equals(Integer.toString(RegexUtilConstants.REGEX))) {
                    ruleLayout.setHTML(3,0,"Enter regular expression: ");
                    ruleLayout.setWidget(3,1, new TextArea());
                } else {
                    ruleLayout.setHTML(3,0,"Enter filter data: ");
                    ruleLayout.setWidget(3,1, new TextBox());
                }
            }
        });
    }

    private void createMergeColumnsRuleLayout() {
        formatRuleLayoutDialogBox();
        ruleLayout.setHTML(0, 0, "Enter Merge Columns Rule Criteria");
        ruleLayout.setHTML(1,0,"Select first column");
        ListBox firstColumnSelector = new ListBox();
        for(int i = 0; i < table.getColumnCount(); i++) {
            firstColumnSelector.addItem(table.getHTML(0, i));
        }
        firstColumnSelector.setSelectedIndex(0);
        firstColumnSelector.setVisibleItemCount(1);
        ruleLayout.setWidget(1, 1, firstColumnSelector);
        ruleLayout.setHTML(2,0,"Select second column");
        ListBox secondColumnSelector = new ListBox();
        for(int i = 0; i < table.getColumnCount(); i++) {
            secondColumnSelector.addItem(table.getHTML(0, i));
        }
        secondColumnSelector.setSelectedIndex(1);
        secondColumnSelector.setVisibleItemCount(1);
        ruleLayout.setWidget(2,1,secondColumnSelector);
    }

    private void createMathFilterRuleLayout() {
        formatRuleLayoutDialogBox();
        ruleLayout.setHTML(0, 0, "Enter Math Filter Rule Criteria");
        ruleLayout.setHTML(1,0,"Select column");
        ListBox firstColumnSelector = new ListBox();
        for(int i = 0; i < table.getColumnCount(); i++) {
            firstColumnSelector.addItem(table.getHTML(0, i));
        }
        firstColumnSelector.setSelectedIndex(0);
        firstColumnSelector.setVisibleItemCount(1);
        ruleLayout.setWidget(1, 1, firstColumnSelector);
        ruleLayout.setHTML(2,0,"Choose criteria:");
        ListBox mathFilterCriteria = new ListBox();
        mathFilterCriteria.setVisibleItemCount(1);
        mathFilterCriteria.addItem(MathUtilConstants.GREATER);
        mathFilterCriteria.addItem(MathUtilConstants.LESSER);
        mathFilterCriteria.addItem(MathUtilConstants.GREATER_EQUAL);
        mathFilterCriteria.addItem(MathUtilConstants.LESSER_EQUAL);
        mathFilterCriteria.addItem(MathUtilConstants.EQUALS2);
        mathFilterCriteria.addItem(MathUtilConstants.NOT_EQUALS);
        mathFilterCriteria.setSelectedIndex(0);
        ruleLayout.setWidget(2, 1, mathFilterCriteria);
        ruleLayout.setHTML(3,0,"Enter number: ");
        ruleLayout.setWidget(3,1, new TextBox());
    }

    private void createDeleteRuleLayout() {
        formatRuleLayoutDialogBox();
        ruleLayout.setHTML(0, 0, "Enter Delete Rule Criteria");
        ruleLayout.setHTML(1,0,"Select column to delete: ");
        ListBox firstColumnSelector = new ListBox();
        for(int i = 0; i < table.getColumnCount(); i++) {
            firstColumnSelector.addItem(table.getHTML(0, i));
        }
        firstColumnSelector.setSelectedIndex(0);
        firstColumnSelector.setVisibleItemCount(1);
        ruleLayout.setWidget(1, 1, firstColumnSelector);
    }

    private void createCloneRuleLayout() {
        formatRuleLayoutDialogBox();
        ruleLayout.setHTML(0, 0, "Enter Clone Rule Criteria");
        ruleLayout.setHTML(1,0,"Select column to clone: ");
        ListBox firstColumnSelector = new ListBox();
        for(int i = 0; i < table.getColumnCount(); i++) {
            firstColumnSelector.addItem(table.getHTML(0, i));
        }
        firstColumnSelector.setSelectedIndex(0);
        firstColumnSelector.setVisibleItemCount(1);
        ruleLayout.setWidget(1, 1, firstColumnSelector);
    }

    private void createChangeColumnPositionRuleLayout() {
        formatRuleLayoutDialogBox();
        ruleLayout.setHTML(0, 0, "Enter Change Column Rule Criteria");
        ruleLayout.setHTML(1,0,"Select column to move: ");
        ListBox firstColumnSelector = new ListBox();
        for(int i = 0; i < table.getColumnCount(); i++) {
            firstColumnSelector.addItem(table.getHTML(0, i));
        }
        firstColumnSelector.setSelectedIndex(0);
        firstColumnSelector.setVisibleItemCount(1);
        ruleLayout.setWidget(1, 1, firstColumnSelector);

        ruleLayout.setHTML(2,0,"Select destination place: ");
        ListBox secondColumnSelector = new ListBox();
        for(int i = 0; i < table.getColumnCount(); i++) {
            if(firstColumnSelector.getSelectedIndex() == i) {
                secondColumnSelector.addItem("No action", Integer.toString(i));
            } else {
                secondColumnSelector.addItem("After " + table.getHTML(0, i), Integer.toString(i));
            }
        }
        secondColumnSelector.setSelectedIndex(firstColumnSelector.getSelectedIndex());
        secondColumnSelector.setVisibleItemCount(1);
        ruleLayout.setWidget(2, 1, secondColumnSelector);
        firstColumnSelector.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                secondColumnSelector.clear();
                for(int i = 0; i < table.getColumnCount(); i++) {
                    if(firstColumnSelector.getSelectedIndex() == i) {
                        secondColumnSelector.addItem("No action", Integer.toString(i));
                    } else if (i < firstColumnSelector.getSelectedIndex()){
                        secondColumnSelector.addItem("Before " + table.getHTML(0, i), Integer.toString(i));
                    } else {
                        secondColumnSelector.addItem("After " + table.getHTML(0, i), Integer.toString(i));
                    }
                }
                secondColumnSelector.setSelectedIndex(firstColumnSelector.getSelectedIndex());
            }
        });

    }

    private void createCalculateRuleLayout() {
        formatRuleLayoutDialogBox();
        ruleLayout.setHTML(0, 0, "Enter Calculate Rule Criteria");
        ruleLayout.setHTML(1,0,"Select first column");
        ListBox firstColumnSelector = new ListBox();
        for(int i = 0; i < table.getColumnCount(); i++) {
            firstColumnSelector.addItem(table.getHTML(0, i));
        }
        firstColumnSelector.setSelectedIndex(0);
        firstColumnSelector.setVisibleItemCount(1);
        ruleLayout.setWidget(1, 1, firstColumnSelector);
        ruleLayout.setHTML(2,0,"Select second column");
        ListBox secondColumnSelector = new ListBox();
        for(int i = 0; i < table.getColumnCount(); i++) {
            secondColumnSelector.addItem(table.getHTML(0, i));
        }
        secondColumnSelector.setSelectedIndex(1);
        secondColumnSelector.setVisibleItemCount(1);
        ruleLayout.setWidget(2, 1, secondColumnSelector);
        ruleLayout.setHTML(3,0,"Choose operation");
        ListBox calculateOperation = new ListBox();
        calculateOperation.setVisibleItemCount(1);
        calculateOperation.addItem(MathUtilConstants.MULTIPLY);
        calculateOperation.addItem(MathUtilConstants.DIVIDE);
        calculateOperation.addItem(MathUtilConstants.ADD);
        calculateOperation.addItem(MathUtilConstants.SUBTRACT);
        calculateOperation.addItem(MathUtilConstants.MOD);
        calculateOperation.setSelectedIndex(0);
        ruleLayout.setWidget(3, 1, calculateOperation);
    }

    private void formatRuleLayoutDialogBox() {
        ruleLayout.setCellSpacing(6);
        FlexTable.FlexCellFormatter cellFormatter = ruleLayout.getFlexCellFormatter();
        cellFormatter.setColSpan(0, 0, 2);
        cellFormatter.setHorizontalAlignment(
                0, 0, HasHorizontalAlignment.ALIGN_CENTER);
    }

    private void addRuleFlowElement(String ruleText){
        FocusPanel ruleItemWrapper = new FocusPanel();
        Label ruleTextLabel = new Label(ruleText);
        ruleItemWrapper.addStyleName(StylesheetNames.RULES_FLOW_PANEL);
        ruleTextLabel.addStyleName(StylesheetNames.RULES_FLOW_PANEL_ITEM);
        ruleTextLabel.addStyleName(StylesheetNames.RULES_FLOW_PANEL_ITEM_CLOSE_BUTTON);
        ruleItemWrapper.add(ruleTextLabel);
        ruleItemWrapper.addClickHandler(new FlowPanelHandler());
        ruleItemWrapper.addMouseOverHandler(new FlowPanelMouseOverHandler());
        ruleItemWrapper.addMouseOutHandler(new FlowPanelMouseOverHandler());
        flowPanel.add(ruleItemWrapper);
    }

    class ApplyRuleButtonClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent clickEvent) {
            if(tableLoadingService == null) {
                tableLoadingService = GWT.create(TableLoadingService.class);
            }

            AsyncCallback<String[][]> callback = new AsyncCallback<String[][]>() {
                @Override
                public void onFailure(Throwable caught) {
                }

                @Override
                public void onSuccess(String[][] result) {
                    table.clear();
                    tableLoadingService.updateRemovalMap(new AsyncCallback<Map<Integer, List<Integer>>>() {
                        @Override
                        public void onFailure(Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(Map<Integer, List<Integer>> map) {
                            rulesRemovalMap = map;
                        }
                    });
                    populateTable(result);
                }
            };
            AbstractRuleDTO rule;
            int firstColumnId;
            int secondColumnId;
            switch (rulesDropBox.getItemText(rulesDropBox.getSelectedIndex())) {
                case RuleNameConstants.CALCULATE:
                    firstColumnId = ((ListBox)ruleLayout.getWidget(1,1)).getSelectedIndex();
                    secondColumnId = ((ListBox)ruleLayout.getWidget(2,1)).getSelectedIndex();
                    String operation = ((ListBox)ruleLayout.getWidget(3,1)).getSelectedValue();
                    addRuleFlowElement("Calculate[("
                            + firstColumnId +") " + operation + " (" + secondColumnId
                            + ")]");
                    rule = new CalculateDTO(firstColumnId,secondColumnId,operation);
                    break;
                case RuleNameConstants.CHANGE_COLUMN_POSITION:
                    firstColumnId = ((ListBox)ruleLayout.getWidget(1,1)).getSelectedIndex();
                    secondColumnId = ((ListBox)ruleLayout.getWidget(2,1)).getSelectedIndex();
                    addRuleFlowElement("Move[("
                            + firstColumnId +") -> (" + secondColumnId + ")]");
                    rule = new ChangeColumnPositionDTO(firstColumnId,secondColumnId);
                    break;
                case RuleNameConstants.CLONE:
                    firstColumnId = ((ListBox)ruleLayout.getWidget(1,1)).getSelectedIndex();
                    addRuleFlowElement("Clone[(" + firstColumnId + ")]");
                    rule = new CloneDTO(firstColumnId);
                    break;
                case RuleNameConstants.DELETE:
                    firstColumnId = ((ListBox)ruleLayout.getWidget(1,1)).getSelectedIndex();
                    addRuleFlowElement("Delete[(" + firstColumnId + ")]");
                    rule = new DeleteDTO(firstColumnId);
                    break;
                case RuleNameConstants.MATH_FILTER:
                    firstColumnId = ((ListBox)ruleLayout.getWidget(1,1)).getSelectedIndex();
                    String criteria = ((ListBox)ruleLayout.getWidget(2,1)).getSelectedValue();
                    double value = Double.parseDouble(((TextBox)ruleLayout.getWidget(3,1)).getText());
                    addRuleFlowElement("Filter[("
                            + firstColumnId +") " + criteria + " (" + value
                            + ")]");
                    rule = new MathFilterDTO(firstColumnId,criteria,value);
                    break;
                case RuleNameConstants.MERGE:
                    firstColumnId = ((ListBox)ruleLayout.getWidget(1,1)).getSelectedIndex();
                    secondColumnId = ((ListBox)ruleLayout.getWidget(2,1)).getSelectedIndex();
                    addRuleFlowElement("Merge[("
                            + firstColumnId +") & (" + secondColumnId + ")]");
                    rule = new MergeColumnsDTO(firstColumnId,secondColumnId);
                    break;
                case RuleNameConstants.REGEX:
                    firstColumnId = ((ListBox)ruleLayout.getWidget(1,1)).getSelectedIndex();
                    int searchType = Integer.parseInt(((ListBox)ruleLayout.getWidget(2,1)).getSelectedValue());
                    String searchData = null;
                    if(searchType == RegexUtilConstants.REGEX) {
                        searchData = ((TextArea)ruleLayout.getWidget(3,1)).getText();
                    } else {
                        searchData = ((TextBox)ruleLayout.getWidget(3,1)).getText();
                    }
                    addRuleFlowElement("Filter[("
                            + firstColumnId +") " + searchType + " (" + searchData
                            + ")]");
                    rule = new RegexFilterDTO(firstColumnId,searchType,searchData);
                    break;
                default:
                    rule = null;
                    break;
            }
            ruleLayout.clear();
            addRuleDialogBox.clear();
            rulesDropBox.clear();
            addRuleDialogBox.hide();
            tableLoadingService.getFilteredTableData(rule, callback);
        }
    }

    class RulesDropBoxChangeHandler implements ChangeHandler {
        @Override
        public void onChange(ChangeEvent changeEvent) {
            ruleLayout.removeAllRows();
            ruleLayout.clear();
            switch (rulesDropBox.getItemText(rulesDropBox.getSelectedIndex())) {
                case RuleNameConstants.CALCULATE:
                    createCalculateRuleLayout();
                    break;
                case RuleNameConstants.CHANGE_COLUMN_POSITION:
                    createChangeColumnPositionRuleLayout();
                    break;
                case RuleNameConstants.CLONE:
                    createCloneRuleLayout();
                    break;
                case RuleNameConstants.DELETE:
                    createDeleteRuleLayout();
                    break;
                case RuleNameConstants.MATH_FILTER:
                    createMathFilterRuleLayout();
                    break;
                case RuleNameConstants.MERGE:
                    createMergeColumnsRuleLayout();
                    break;
                case RuleNameConstants.REGEX:
                    createRegexFilterRuleLayout();
                    break;
                default:
                    break;
            }
        }
    }

    class FlowPanelHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            Widget currentWidget = (Widget)event.getSource();
            if(currentWidget != null){
//                flowPanel.getWidgetIndex(currentWidget);
                flowPanel.remove(currentWidget);
            }
        }
    }

    class FlowPanelMouseOverHandler implements MouseOverHandler, MouseOutHandler {
        @Override
        public void onMouseOver(MouseOverEvent mouseOverEvent) {
            Widget currentWidget = (Widget)mouseOverEvent.getSource();
            int index = flowPanel.getWidgetIndex(currentWidget);
            List<Integer> rules = rulesRemovalMap.get(index);
            currentWidget.addStyleName(StylesheetNames.RULES_FLOW_PANEL_MOUSE_OVER);
            for(Integer i: rules) {
                Widget temp = flowPanel.getWidget(i);
                temp.addStyleName(StylesheetNames.RULES_FLOW_PANEL_MOUSE_OVER);
            }
        }

        @Override
        public void onMouseOut(MouseOutEvent mouseOutEvent) {
            Widget currentWidget = (Widget)mouseOutEvent.getSource();
            int index = flowPanel.getWidgetIndex(currentWidget);
            List<Integer> rules = rulesRemovalMap.get(index);
            currentWidget.removeStyleName(StylesheetNames.RULES_FLOW_PANEL_MOUSE_OVER);
            for(Integer i: rules) {
                Widget temp = flowPanel.getWidget(i);
                temp.removeStyleName(StylesheetNames.RULES_FLOW_PANEL_MOUSE_OVER);
            }
        }
    }
}
