package com.github.jakz.chemquiz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import com.pixbits.lib.ui.table.ColumnSpec;
import com.pixbits.lib.ui.table.DataSource;
import com.pixbits.lib.ui.table.FilterableDataSource;
import com.pixbits.lib.ui.table.SimpleListSelectionListener;
import com.pixbits.lib.ui.table.TableModel;

public class MoleculeTable extends JPanel
{
  private final Mediator mediator;
  
  private FilterableDataSource<Molecule> data;
  
  private TableModel<Molecule> model;
  private JTable table;
  
  public MoleculeTable(Mediator mediator)
  {
    this.mediator = mediator;
    
    table = new JTable();
    table.setAutoCreateRowSorter(true);
    model = new Model(table);
    
    setLayout(new BorderLayout());
    JScrollPane pane = new JScrollPane(table);
    pane.setPreferredSize(new Dimension(600, 500));
    add(pane, BorderLayout.CENTER);

    model.addColumn(new ColumnSpec<Molecule, ImageIcon>("", ImageIcon.class, mediator.cache::get).setWidth(220));
    model.addColumn(new ColumnSpec<>("Name", String.class, m -> String.format("%s - %s (%s)", m.formula, m.traditional[0], m.iupac)));
       
    model.setRowHeight(220);
  }
  
  public void refresh(Collection<Molecule> data)
  {
    this.data = FilterableDataSource.of(data);
    model.setData(this.data);
    model.fireTableDataChanged();
  }
  
  private class Model extends TableModel<Molecule>
  {
    Model(JTable table)
    {
      super(table, DataSource.empty());
    }
  } 
}
