/*
 * Copyright 2013 SmartBear Software
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the Licence for the specific language governing permissions and limitations
 * under the Licence.
 */
package org.loadui.testfx;

import com.google.common.base.Predicate;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBoxBuilder;
import javafx.util.Callback;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.categories.TestFX;
import org.loadui.testfx.controls.Commons;
import org.loadui.testfx.controls.TableViews;

import static javafx.collections.FXCollections.observableArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.TableViews.containsCell;
import static org.loadui.testfx.controls.TableViews.numberOfRowsIn;

@Category( TestFX.class )
public class TableViewsTest extends GuiTest
{
	@Test
	public void shouldFindCellValues()
	{
		verifyThat(".table-view", containsCell("9"));
        verifyThat(".table-view", not(containsCell("1")) );

        TableView<?> tableView = find(".table-view");
        verifyThat(tableView, containsCell(9));
	}

    @Test
    public void shouldFindCellValues_usingPredicate()
    {
        verifyThat(".table-view", containsCell( new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return Integer.parseInt(s) == 9;
            }
        }));
    }

    @Test
    public void shouldHaveCorrectNumberOfRows()
    {
        verifyThat( numberOfRowsIn(".table-view"), is(3));

        TableView<?> tableView = find(".table-view");
        verifyThat( numberOfRowsIn( tableView ), is(3));
    }

    @Override
    protected Parent getRootNode()
    {
        TableView<Integer> table = new TableView<>();
        table.setItems(observableArrayList(1, 2, 3));
        TableColumn<Integer, Integer> column = new TableColumn<>("col");
        column.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Integer, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Integer, Integer> f) {
                return new SimpleObjectProperty( f.getValue() * 3 );
            }
        });
        table.getColumns().add(column);
        return VBoxBuilder
                .create()
                .children( table ).build();
    }
}
