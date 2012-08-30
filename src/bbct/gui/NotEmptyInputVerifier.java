/*
 * This file is part of BBCT.
 *
 * Copyright 2012 codeguru <codeguru@users.sourceforge.net>
 *
 * BBCT is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BBCT is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package bbct.gui;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * Validates that a JTextField is not empty.
 *
 * @author codeguru <codeguru@users.sourceforge.net>
 */
public class NotEmptyInputVerifier extends InputVerifier {

    /**
     * Validates that a JTextField is not empty.
     *
     * @param jc The JTextField to validate.
     * @return
     */
    @Override
    public boolean verify(JComponent jc) {
        return !((JTextField) jc).getText().equals("");
    }

    /**
     * This InputVerifier always yields focus. Input verification should be done
     * at a more convenient time as to minimally disrupt the user experience.
     *
     * @param The JComponent to verify
     * @return true
     */
    @Override
    public boolean shouldYieldFocus(JComponent jc) {
        return true;
    }
}
