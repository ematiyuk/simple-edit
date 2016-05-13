package simpleedit;

/*
 * JCommon : a free general purpose class library for the Java(tm) platform
 *
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ----------------------
 * FontChooserDialog.java
 * ----------------------
 * (C) Copyright 2000-2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: FontChooserDialog.java,v 1.5 2007/11/02 17:50:36 taqua Exp $
 *
 * Changes (from 26-Oct-2001)
 * --------------------------
 * 26-Oct-2001 : Changed package to com.jrefinery.ui.*;
 * 14-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 *
 */
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;


/*
 * JCommon : a free general purpose class library for the Java(tm) platform
 * s
 *
 * (C) Copyright 2000-2008, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ---------------------
 * FontChooserPanel.java
 * ---------------------
 * (C) Copyright 2000-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Arnaud Lelievre;
 *
 * $Id: FontChooserPanel.java,v 1.6 2008/12/18 09:57:32 mungady Exp $
 *
 * Changes (from 26-Oct-2001)
 * --------------------------
 * 26-Oct-2001 : Changed package to com.jrefinery.ui.*;
 * 14-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 08-Sep-2003 : Added internationalization via use of properties resourceBundle (RFE 690236) (AL);
 * 21-Feb-2004 : The FontParameter of the constructor was never used (TM);
 * 18-Dec-2008 : Use ResourceBundleWrapper - see JFreeChart patch 1607918 by
 *               Jess Thrysoee (DG);
 *
 */
/**
 * A dialog for choosing a font from the available system fonts.
 *
 * @author David Gilbert
 */
public class FontChooserDialog extends StandardDialog {

    /** The panel within the dialog that contains the font selection controls. */
    private FontChooserPanel fontChooserPanel;

    /**
     * Standard constructor - builds a font chooser dialog owned by another dialog.
     *
     * @param owner  the dialog that 'owns' this dialog.
     * @param title  the title for the dialog.
     * @param modal  a boolean that indicates whether or not the dialog is modal.
     * @param font  the initial font displayed.
     */
    public FontChooserDialog(final Dialog owner, final String title, final boolean modal, final Font font) {
        super(owner, title, modal);
        setContentPane(createContent(font));
    }

    /**
     * Standard constructor - builds a font chooser dialog owned by a frame.
     *
     * @param owner  the frame that 'owns' this dialog.
     * @param title  the title for the dialog.
     * @param modal  a boolean that indicates whether or not the dialog is modal.
     * @param font  the initial font displayed.
     */
    public FontChooserDialog(final Frame owner, final String title, final boolean modal, final Font font) {
        super(owner, title, modal);
        setContentPane(createContent(font));
    }

    /**
     * Returns the selected font.
     *
     * @return the font.
     */
    public Font getSelectedFont() {
        return this.fontChooserPanel.getSelectedFont();
    }

    /*
     * Set the selected font in the fontControlPanel
     *
     * created by MaJe
     */
    public void setSelectedFont(Font font) {
        this.fontChooserPanel.setSelectedFont(font);
    }

    /**
     * Returns the panel that is the user interface.
     *
     * @param font  the font.
     *
     * @return the panel.
     */
    private JPanel createContent(Font font) {
        final JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        if (font == null) {
            font = new Font("Dialog", 10, Font.PLAIN);
        }
        this.fontChooserPanel = new FontChooserPanel(font);
        content.add(this.fontChooserPanel);

        final JPanel buttons = createButtonPanel();
        buttons.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        content.add(buttons, BorderLayout.SOUTH);

        return content;
    }
}

/**
 * A panel for choosing a font from the available system fonts - still a bit of
 * a hack at the moment, but good enough for demonstration applications.
 *
 * @author David Gilbert
 */
class FontChooserPanel extends JPanel {

    /** The font sizes that can be selected. */
    public static final String[] SIZES = {"9", "10", "11", "12", "14", "16",
        "18", "20", "22", "24", "28", "36", "48", "72"};
    /** The list of fonts. */
    private JList fontlist;
    /** The list of sizes. */
    private JList sizelist;
    /** The checkbox that indicates whether the font is bold. */
    private JCheckBox bold;
    /** The checkbox that indicates whether or not the font is italic. */
    private JCheckBox italic;

    /**
     * Standard constructor - builds a FontChooserPanel initialised with the
     * specified font.
     *
     * @param font  the initial font to display.
     */
    public FontChooserPanel(final Font font) {

        final GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final String[] fonts = g.getAvailableFontFamilyNames();

        setLayout(new BorderLayout());
        final JPanel right = new JPanel(new BorderLayout());

        final JPanel fontPanel = new JPanel(new BorderLayout());
        fontPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Font"));
        this.fontlist = new JList(fonts);
        final JScrollPane fontpane = new JScrollPane(this.fontlist);
        fontpane.setBorder(BorderFactory.createEtchedBorder());
        fontPanel.add(fontpane);
        add(fontPanel);

        final JPanel sizePanel = new JPanel(new BorderLayout());
        sizePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Size"));
        this.sizelist = new JList(SIZES);
        final JScrollPane sizepane = new JScrollPane(this.sizelist);
        sizepane.setBorder(BorderFactory.createEtchedBorder());
        sizePanel.add(sizepane);

        final JPanel attributes = new JPanel(new GridLayout(1, 2));
        this.bold = new JCheckBox("Bold");
        this.italic = new JCheckBox("Italic");
        attributes.add(this.bold);
        attributes.add(this.italic);
        attributes.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Attributes"));

        right.add(sizePanel, BorderLayout.CENTER);
        right.add(attributes, BorderLayout.SOUTH);

        add(right, BorderLayout.EAST);

        setSelectedFont(font);
    }

    /**
     * Returns a Font object representing the selection in the panel.
     *
     * @return the font.
     */
    public Font getSelectedFont() {
        return new Font(getSelectedName(), getSelectedStyle(),
                getSelectedSize());
    }

    /**
     * Returns the selected name.
     *
     * @return the name.
     */
    public String getSelectedName() {
        return (String) this.fontlist.getSelectedValue();
    }

    /**
     * Returns the selected style.
     *
     * @return the style.
     */
    public int getSelectedStyle() {
        if (this.bold.isSelected() && this.italic.isSelected()) {
            return Font.BOLD + Font.ITALIC;
        }
        if (this.bold.isSelected()) {
            return Font.BOLD;
        }
        if (this.italic.isSelected()) {
            return Font.ITALIC;
        } else {
            return Font.PLAIN;
        }
    }

    /**
     * Returns the selected size.
     *
     * @return the size.
     */
    public int getSelectedSize() {
        final String selected = (String) this.sizelist.getSelectedValue();
        if (selected != null) {
            return Integer.parseInt(selected);
        } else {
            return 10;
        }
    }

    /**
     * Initializes the contents of the dialog from the given font
     * object.
     *
     * @param font the font from which to read the properties.
     */
    public void setSelectedFont(final Font font) {
        if (font == null) {
            throw new NullPointerException();
        }
        this.bold.setSelected(font.isBold());
        this.italic.setSelected(font.isItalic());

        final String fontName = font.getName();
        ListModel model = this.fontlist.getModel();
        this.fontlist.clearSelection();
        for (int i = 0; i < model.getSize(); i++) {
            if (fontName.equals(model.getElementAt(i))) {
                this.fontlist.setSelectedIndex(i);
                break;
            }
        }

        final String fontSize = String.valueOf(font.getSize());
        model = this.sizelist.getModel();
        this.sizelist.clearSelection();
        for (int i = 0; i < model.getSize(); i++) {
            if (fontSize.equals(model.getElementAt(i))) {
                this.sizelist.setSelectedIndex(i);
                break;
            }
        }
    }
}

/*
 * JCommon : a free general purpose class library for the Java(tm) platform
 *
 *
 * (C) Copyright 2000-2008, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * -------------------
 * StandardDialog.java
 * -------------------
 * (C) Copyright 2000-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Arnaud Lelievre;
 *
 * $Id: StandardDialog.java,v 1.7 2008/12/18 09:57:32 mungady Exp $
 *
 * Changes (from 26-Oct-2001)
 * --------------------------
 * 26-Oct-2001 : Changed package to com.jrefinery.ui.*;
 * 08-Sep-2003 : Added internationalization via use of properties
 *               resourceBundle (RFE 690236) (AL);
 * 18-Dec-2008 : Use ResourceBundleWrapper - see JFreeChart patch 1607918 by
 *               Jess Thrysoee (DG);
 *
 */
/**
 * The base class for standard dialogs.
 *
 * @author David Gilbert
 */
class StandardDialog extends JDialog implements ActionListener {

    /** Flag that indicates whether or not the dialog was cancelled. */
    private boolean cancelled;

    /**
     * Standard constructor - builds a dialog...
     *
     * @param owner  the owner.
     * @param title  the title.
     * @param modal  modal?
     */
    public StandardDialog(final Frame owner, final String title,
            final boolean modal) {
        super(owner, title, modal);
        this.cancelled = false;
    }

    /**
     * Standard constructor - builds a dialog...
     *
     * @param owner  the owner.
     * @param title  the title.
     * @param modal  modal?
     */
    public StandardDialog(final Dialog owner, final String title,
            final boolean modal) {
        super(owner, title, modal);
        this.cancelled = false;
    }

    /**
     * Returns a flag that indicates whether or not the dialog has been
     * cancelled.
     *
     * @return boolean.
     */
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Handles clicks on the standard buttons.
     *
     * @param event  the event.
     */
    public void actionPerformed(final ActionEvent event) {
        final String command = event.getActionCommand();
        if (command.equals("helpButton")) {
            // display help information
        } else if (command.equals("okButton")) {
            this.cancelled = false;
            setVisible(false);
        } else if (command.equals("cancelButton")) {
            this.cancelled = true;
            setVisible(false);
        }
    }

    /**
     * Builds and returns the user interface for the dialog.  This method is
     * shared among the constructors.
     *
     * @return the button panel.
     */
    protected JPanel createButtonPanel() {

        final L1R2ButtonPanel buttons = new L1R2ButtonPanel(
                "Help",
                "OK",
                "Cancel");


        final JButton helpButton = buttons.getLeftButton();
        helpButton.setActionCommand("helpButton");
        helpButton.addActionListener(this);
        helpButton.setVisible(false);

        final JButton okButton = buttons.getRightButton1();
        okButton.setActionCommand("okButton");
        okButton.addActionListener(this);

        final JButton cancelButton = buttons.getRightButton2();
        cancelButton.setActionCommand("cancelButton");
        cancelButton.addActionListener(this);

        buttons.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        return buttons;
    }
}

/*
 * JCommon : a free general purpose class library for the Java(tm) platform
 *
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * --------------------
 * L1R2ButtonPanel.java
 * --------------------
 * (C) Copyright 2000-2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: L1R2ButtonPanel.java,v 1.4 2007/11/02 17:50:36 taqua Exp $
 *
 * Changes (from 26-Oct-2001)
 * --------------------------
 * 26-Oct-2001 : Changed package to com.jrefinery.ui.*;
 * 26-Jun-2002 : Removed unnecessary import (DG);
 * 14-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 *
 */
/**
 * A 'ready-made' panel that has one button on the left and two buttons on the right - nested
 * panels and layout managers take care of resizing.
 *
 * @author David Gilbert
 */
class L1R2ButtonPanel extends JPanel {

    /** The left button. */
    private JButton left;
    /** The first button on the right of the panel. */
    private JButton right1;
    /** The second button on the right of the panel. */
    private JButton right2;

    /**
     * Standard constructor - creates a three button panel with the specified button labels.
     *
     * @param label1  the label for button 1.
     * @param label2  the label for button 2.
     * @param label3  the label for button 3.
     */
    public L1R2ButtonPanel(final String label1, final String label2, final String label3) {

        setLayout(new BorderLayout());

        // create the pieces...
        this.left = new JButton(label1);

        final JPanel rightButtonPanel = new JPanel(new GridLayout(1, 2));
        this.right1 = new JButton(label2);
        this.right2 = new JButton(label3);
        rightButtonPanel.add(this.right1);
        rightButtonPanel.add(this.right2);

        // ...and put them together
        add(this.left, BorderLayout.WEST);
        add(rightButtonPanel, BorderLayout.EAST);

    }

    /**
     * Returns a reference to button 1, allowing the caller to set labels, action-listeners etc.
     *
     * @return the left button.
     */
    public JButton getLeftButton() {
        return this.left;
    }

    /**
     * Returns a reference to button 2, allowing the caller to set labels, action-listeners etc.
     *
     * @return the right button 1.
     */
    public JButton getRightButton1() {
        return this.right1;
    }

    /**
     * Returns a reference to button 3, allowing the caller to set labels, action-listeners etc.
     *
     * @return  the right button 2.
     */
    public JButton getRightButton2() {
        return this.right2;
    }
}
/*
 * JCommon : a free general purpose class library for the Java(tm) platform
 *
 *
 * (C) Copyright 2000-2008, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ---------------------
 * FontDisplayField.java
 * ---------------------
 * (C) Copyright 2000-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Arnaud Lelievre;
 *
 * $Id: FontDisplayField.java,v 1.6 2008/12/18 09:57:32 mungady Exp $
 *
 * Changes (from 26-Oct-2001)
 * ----------------------------------
 * 26-Oct-2001 : Changed package to com.jrefinery.ui.*;
 * 14-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 08-Sep-2003 : Added internationalization via use of properties
 *               resourceBundle (RFE 690236) (AL);
 * 18-Dec-2008 : Use ResourceBundleWrapper - see JFreeChart patch 1607918 by
 *               Jess Thrysoee (DG);
 *
 */

/**
 * A field for displaying a font selection.  The display field itself is
 * read-only, to the developer must provide another mechanism to allow the
 * user to change the font.
 *
 * @author David Gilbert
 */
class FontDisplayField extends JTextField {

    /** The current font. */
    private Font displayFont;

    /**
     * Standard constructor - builds a FontDescriptionField initialised with
     * the specified font.
     *
     * @param font  the font.
     */
    public FontDisplayField(final Font font) {
        super("");
        setDisplayFont(font);
        setEnabled(false);
    }

    /**
     * Returns the current font.
     *
     * @return the font.
     */
    public Font getDisplayFont() {
        return this.displayFont;
    }

    /**
     * Sets the font.
     *
     * @param font  the font.
     */
    public void setDisplayFont(final Font font) {
        this.displayFont = font;
        setText(fontToString(this.displayFont));
    }

    /**
     * Returns a string representation of the specified font.
     *
     * @param font  the font.
     *
     * @return a string describing the font.
     */
    private String fontToString(final Font font) {
        if (font != null) {
            return font.getFontName() + ", " + font.getSize();
        } else {
            return "No Font Selected";
        }
    }
}
