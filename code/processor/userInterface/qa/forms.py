# forms.py
from django import forms

FIVE_W_CHOICES = (("","Select--"),
                ("who","Who"),
                ("what","What"),
                ("when","When"),
                ("where","Where"))

COL_2_CHOICES = (("","Please select in order"),
                ("is","is"),
                ("does","does"),
                ("was","was"),
                ("all","all"),
                ("were","were"),
                ("are","are"),
                ("did","did"))

LAST_COL_CHOICES = (("","Please select in order"),
                    ("born","born"),
                    ('producer','producer'),
                    ('director','director'),
                    ('writer','writer'),
                    ('scriptwriter','scriptwriter'),
                    ('editor','editor'),
                    ('distributor','distributor'),
                    ('filmeditor','film-editor'),
                    ('narrator','narrator'),
                    ('cinematographer','cinematographer'),
                    ('music','music-composer'),
                    ('starring','actors'),
                    ('starring','stars'),
                    ('studio','studio'),
                    ('website','website'),
                    ('title','title'),
                    ('education','education'),
                    ('basedon','basedon'),
                    ('story','story'),
                    ('shot','shot'),
                    ('directed','directed'),
                    ('completed','completed'),
                    ('released','released'),
                    ('release','release'),
                    ('runningtime','running-time'),
                    ('language','language'),
                    ('budget','budget'),
                    ('cost','cost'),
                    ('revenue','revenue'),
                    ('boxofficereturn','box-office-return'),
                    ("spouse","spouse"),
                    ("partner","partner"),
                    ("employer","employer"),
                    ("caption","caption"),
                    ("family","family"),
                    ("children","children"),
                    ("parents","parents"),
                    ("work","work"),
                    ("live","live"),
                    ("yearsactive","active-years"),
                    ("from","from"),
                    ("residence","residence"),
                    ("die","die"),
                    ("knownfor","known-for"),
                    ("birthplace","birth-place"),
                    ("birthname","birth-name"),
                    ("almamater","almamater"),
                    ("occupation","occupation"),
                    ("ethnicity","ethnicity"),
                    ("nationality","nationality"),
                    ("height","height"),
                    ("weight","weight"),
                    ("citizenship","citizenship"),
                    ("networth","networth"),
                    ("knownfor","fame"),
                    ("othernames","nicknames"),
                    ("othernames","other-names"),
                    ("othernames","nickname"),
                    ("awards","awards"),
                    ("salary","salary"))

NUM_CHOICES = (("first","first"),
                ("second","second"),
                ("third","third"))

QTYPE_CHOICES = (("person","Person"),
                ("places","Places"),
                ("film","Movies"))

select_box_errors = {'required':'Please select a value',
                     'invalid':'Please select a value'}

input_box_errors = {'required':'Please enter a value',
                    'invalid':'Invalid value'}

class QuestionForm(forms.Form):
    qtype = forms.ChoiceField(choices=QTYPE_CHOICES, 
                              widget=forms.Select(attrs={'class':'form-control qtype'}),
                              error_messages = select_box_errors)
    fiveW = forms.ChoiceField(choices=FIVE_W_CHOICES, 
                              widget=forms.Select(attrs={'class':'form-control'}),
                              error_messages = select_box_errors)
    col2 = forms.ChoiceField(choices=COL_2_CHOICES, 
                             widget=forms.Select(attrs={'class':'form-control'}),
                             error_messages = select_box_errors)
    noun = forms.CharField(max_length = 100, 
                           widget=forms.TextInput(attrs={'class':'form-control',
                                                         'placeholder':'Enter a Person Name', 
                                                         'data-provide':"typeahead", 
                                                         "autocomplete":"off"}),
                           error_messages = input_box_errors)
    last_col = forms.ChoiceField(choices=LAST_COL_CHOICES, 
                                 widget=forms.Select(attrs={'class':'form-control'}),
                                 error_messages = select_box_errors)
    num_col = forms.ChoiceField(choices=NUM_CHOICES, 
                                widget=forms.Select(attrs={'class':'form-control hid'}),
                                error_messages = select_box_errors)

