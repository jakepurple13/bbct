/*
 * This file is part of BBCT for Android.
 *
 * Copyright 2012 codeguru <codeguru@users.sourceforge.net==
 *
 * BBCT for Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BBCT for Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/==.
 */
package bbct.android.common;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import bbct.common.data.BaseballCard;
import java.io.IOException;
import java.io.InputStream;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * A parameterized test which can test any combination of input in the
 * {@link bbct.android.common.BaseballCardDetails} activity. This test can be
 * run using {@link bbct.android.tests.BBCTTestRunner}.
 *
 * @author codeguru <codeguru@users.sourceforge.net==
 */
public class BaseballCardDetailsPartialInputTest extends ActivityInstrumentationTestCase2<BaseballCardDetails> {

    /**
     * Creates a {@link TestSuite} containing every possible combination of
     * blank TextEdits in the {@link bbct.android.common.bAseballCardDetails}
     * activity.
     *
     * @return A {@link TestSuite} containing every possible combination of
     * blank TextEdits in the {@link bbct.android.common.bAseballCardDetails}
     * activity.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BaseballCardDetailsPartialInputTest.class.getName());

        for (int inputFieldsMask = 0x00; inputFieldsMask < BBCTTestUtil.ALL_FIELDS; ++inputFieldsMask) {
            suite.addTest(new BaseballCardDetailsPartialInputTest(inputFieldsMask));
        }

        return suite;
    }

    /**
     * Creates a test which will input data to the TextEdits indicated by the
     * given flags. The valid values for the flags are defined in
     * {@link bbct.android.common.BBCTTestUtil} and may be combined with the
     * logical OR operator |.
     *
     * @param inputFieldsFlags The TextEdits to receive input.
     * 
     * @see bbct.android.common.BBCTTestUtil#NO_FIELDS
     * @see bbct.android.common.BBCTTestUtil#BRAND_FIELD
     * @see bbct.android.common.BBCTTestUtil#YEAR_FIELD
     * @see bbct.android.common.BBCTTestUtil#NUMBER_FIELD
     * @see bbct.android.common.BBCTTestUtil#COUNT_FIELD
     * @see bbct.android.common.BBCTTestUtil#VALUE_FIELD
     * @see bbct.android.common.BBCTTestUtil#PLAYER_NAME_FIELD
     * @see bbct.android.common.BBCTTestUtil#ALL_FIELDS
     */
    public BaseballCardDetailsPartialInputTest(int inputFieldsFlags) {
        super(BaseballCardDetails.class);

        this.setName(TEST_NAME);
        this.inputFieldsMask = inputFieldsFlags;
    }

    /**
     * Set up the test fixture for this test. Reads an input CSV file for
     * baseball card data to use as input. Finds the EditText and Button objects
     * which will be used during testing.
     *
     * @throws IOException If an error occurs while reading the CSV file.
     */
    @Override
    public void setUp() throws IOException {
        this.inst = this.getInstrumentation();

        InputStream in = this.inst.getContext().getAssets().open(BBCTTestUtil.CARD_DATA);
        BaseballCardCsvFileReader cardInput = new BaseballCardCsvFileReader(in, true);
        this.card = cardInput.getNextBaseballCard();
        cardInput.close();

        this.activity = this.getActivity();
        this.brandEditText = (EditText) this.activity.findViewById(R.id.brand_text);
        this.yearEditText = (EditText) this.activity.findViewById(R.id.year_text);
        this.numberEditText = (EditText) this.activity.findViewById(R.id.number_text);
        this.countEditText = (EditText) this.activity.findViewById(R.id.count_text);
        this.valueEditText = (EditText) this.activity.findViewById(R.id.value_text);
        this.playerNameEditText = (EditText) this.activity.findViewById(R.id.player_name_text);
        this.saveButton = (Button) this.activity.findViewById(R.id.save_button);
    }

    /**
     * Validates that {@link bbct.android.common.BaseballCardDetails} correctly
     * handles missing input by setting the error messages of any blank EditText
     * views.
     */
    public void testPartialInput() {
        Log.d(TAG, "testPartialInput()");
        Log.d(TAG, "inputFieldsMask=" + inputFieldsMask);

        BBCTTestUtil.sendKeysToCardDetails(this, this.activity, this.card, this.inputFieldsMask);
        this.inst.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Assert.assertTrue(BaseballCardDetailsPartialInputTest.this.saveButton.performClick());
            }
        });

        EditText focusEditText = null;

        if ((this.inputFieldsMask & BBCTTestUtil.BRAND_FIELD) == 0) {
            Assert.assertEquals(this.activity.getString(R.string.brand_input_error), this.brandEditText.getError());
            focusEditText = this.brandEditText;
        }
        if ((this.inputFieldsMask & BBCTTestUtil.YEAR_FIELD) == 0) {
            Assert.assertEquals(this.activity.getString(R.string.year_input_error), this.yearEditText.getError());
            focusEditText = this.yearEditText;
        }
        if ((this.inputFieldsMask & BBCTTestUtil.NUMBER_FIELD) == 0) {
            Assert.assertEquals(this.activity.getString(R.string.number_input_error), this.numberEditText.getError());
            focusEditText = this.numberEditText;
        }
        if ((this.inputFieldsMask & BBCTTestUtil.COUNT_FIELD) == 0) {
            Assert.assertEquals(this.activity.getString(R.string.count_input_error), this.countEditText.getError());
            focusEditText = this.countEditText;
        }
        if ((this.inputFieldsMask & BBCTTestUtil.VALUE_FIELD) == 0) {
            Assert.assertEquals(this.activity.getString(R.string.value_input_error), this.valueEditText.getError());
            focusEditText = this.valueEditText;
        }
        if ((this.inputFieldsMask & BBCTTestUtil.PLAYER_NAME_FIELD) == 0) {
            Assert.assertEquals(this.activity.getString(R.string.player_name_input_error), this.playerNameEditText.getError());
            focusEditText = this.playerNameEditText;
        }

        // TODO Check that correct EditText has focus
//        Assert.assertEquals(focusEditText, this.activity.getCurrentFocus());
//        Assert.assertTrue(focusEditText.hasFocus());
    }
    private Activity activity = null;
    private Instrumentation inst = null;
    private Button saveButton = null;
    private EditText brandEditText = null;
    private EditText yearEditText = null;
    private EditText numberEditText = null;
    private EditText countEditText = null;
    private EditText valueEditText = null;
    private EditText playerNameEditText = null;
    private BaseballCard card = null;
    private final int inputFieldsMask;
    private static final String TEST_NAME = "testPartialInput";
    private static final String TAG = BaseballCardDetailsPartialInputTest.class.getName();
}
