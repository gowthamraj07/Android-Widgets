# Format Edit Text
This library helps to format the input (in EditText) while the user types. 
Currently this library supports any formats without dash (-) operator in output, 
as it uses dash (-) operator to specify the format.


## How to use

### 1. Declare FormatEditText in Layout (xml) file
    <com.androidwidgets.formatedittext.widgets.FormatEditText
            android:id="@+id/ed_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="number" />

### 2. Set the format to the FormatEditText in Code
- If you need only format (when no validation required)

        final FormatEditText editText = findViewById(R.id.ed_text);
        editText.setFormat("<any format>");
        
- If you need validation
        
        final FormatEditText editText = findViewById(R.id.ed_text);
        editText.setValidator(new Validator());
        editText.setValidationListener(new ValidationListener());
        editText.setFormat("<any format>");
        

## Examples
### Credit Card formats
| Credit Card Type | Format              |
|------------------|---------------------|
| Visa             | ---- ---- ---- ---- |
| Visa Electron    | ---- ---- ---- ---- |
| American Express | ---- ------ -----   |
| MasterCard       | ---- ---- ---- ---- |
| Maestro          | ---- ---- -----     |
|                  | ---- ------ -----   |
|                  | ---- ---- ---- ---- |


### Date formats
| Date Examples | Format            |
|---------------|-------------------|
| 22/Feb/2019   | --/---/----       |
| 22/Feb/19     | --/---/--         |
| 22-Feb-2019   | Not supported yet |
| 22/02/2019    | --/--/----        |
| 22/02/19      | --/--/--          |


### Mobile number formats ([Reference here](https://en.wikipedia.org/wiki/National_conventions_for_writing_telephone_numbers))
| Country             | Digits         | Mobile number Example | Format          |
|---------------------|----------------|-----------------------|-----------------|
| India               | 10 digits long | +91 AAAAA BBBBB       | +91 ----- ----- |
| Hong Kong and Macau | 8 digits long  | XXXX YYYY             | ---- ----       |
| Japan               |                | (0AA) NXX XXXX        | (---) --- ----  |


### Time formats
| Time example | Format      |
|--------------|-------------|
| 10:50:45 am  | --:--:-- -- |

