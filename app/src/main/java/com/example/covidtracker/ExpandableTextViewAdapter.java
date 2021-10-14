package com.example.covidtracker;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableTextViewAdapter extends BaseExpandableListAdapter {

    Context context;

    String[]faqs= {
            "Where did we get our data and information from?",
            "When is it updated?",
            "Who are we behind the app?",
            "Where do I turn if I want to test for covid-19?",
            "Do I need to test myself several times for Covid-19 and Do I need to test myself for Corona again if I am already showing symptoms?",
            "Should I test myself to see if I have recovered from covid-19?",
            "How is Covid-19 transmitted?",
            "Can you get infected by a person who has covid-19 but who has no symptoms?",
            "What are the symptoms of covid-19?",
            "what different tests are there for covid-19?"

    };
    String[] [] answer= {
            {"The Swedish Public Health Agency, the State and covid.ourworldindata.org/ och API\n"},

            {"The information and data are updated daily and weekly\n"},
            {"We are four University Students who Study at the Computer Science Program at Karlstad University who want to create an app that displays information related to covid-19.\n"},
            {"It is the care that assesses whether you should do a test depending on your symptoms and what the region's guidelines look like. If you feel sick and have symptoms of covid-19, you can read more at 1177.se/covid19-prov to find out how the testing is done where you live.\n" + "\n"
                    + "In some workplaces with close contact with people, where it is not possible to work from home, the employer may decide on regular screening to detect SARS-CoV-2, the virus that causes covid-19. Screening is a supplement to other infection control measures. It is the employer who must inform about the screening and have routines for it"},
            {"You do not usually need to be tested again if you know you have been ill with covid-19 for the past six months. However, you should stay at home until you feel healthy. You may have been told that you have already had covid-19 through a positive PCR test, antigen test or antibody test taken in healthcare. In some cases, a doctor may ask you to get tested again.\n" +
                    "\n" +
                    "If you are ill and have taken a PCR test that is negative for covid-19, you also do not need to take a new test while you are ill.\n" +
                    "\n" +
                    "If you have previously only tested negative for covid-19 and later become ill again with symptoms for covid-19, you should test yourself again."},
            {"No. The tests are used to diagnose when you are ill and not to see if you have recovered or to assess whether you are contagious. Therefore, do not take a new test to see if you have recovered.\n" +
                    "\n" +
                    "Instead, it is the number of days that have passed since you became ill and how long you have been symptom-free that determines when you stop being contagious. You must stay at home and follow the rules of conduct you receive from healthcare and can then return to work, school or other activities."},
            {"Covid-19 is spread mainly through close contacts between people through small and large droplets from the airways. When an infected person sneezes, coughs, speaks or exhales, small drops are spread to the environment. The infection can enter the body both by inhalation or by touching with unclean hands in the eyes or on mucous membranes in the nose and mouth."},
            {"Yes, infection can be spread both from people with and without symptoms. A person without symptoms is called asymptomatic."},
            {"The disease is in most people a respiratory infection and a number of different symptoms can occur, the symptoms reported are mainly:\n" +
                    "\n" +
                    "cough\n" +
                    "fever\n" +
                    "hard to breathe\n" +
                    "snuva\n" +
                    "stuffy nose\n" +
                    "sore throat\n" +
                    "headache\n" +
                    "nausea\n" +
                    "pain in muscles and joints\n" +
                    "altered taste and smell\n" +
                    "diarrhea\n" +
                    "It is not possible to determine if it is covid-19 just by starting from the symptoms. This requires a test that is analyzed by the health service."},
            {"There are tests that can show if you have a current infection and tests that show if you have already had a COVID-19 infection and have developed antibodies.\n" +
                    "\n" +
                    "PCR-tests and rapid antigen tests show if you have an active COVID-19 infection. The PCR test is used today on a large scale. It detects the virus’s genetic material. An antigen test shows if there are viral proteins in the sample.\n" +
                    "\n" +
                    "An antibody test shows if a person has previously been infected with COVID-19 and has developed antibodies in response to the virus. An antibody test is taken 2–3 weeks after falling ill. We recommend that antibody tests be taken by health care providers."}


    };


    public ExpandableTextViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return faqs.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return answer[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return faqs[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return answer [groupPosition] [childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View converView, ViewGroup parent) {

        String questionFaq = (String) getGroup(groupPosition);
        if (converView==null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            converView = inflater.inflate(R.layout.faqs_title, null);
        }
        TextView questionFaq2=converView.findViewById(R.id.fagsTitleView);
        questionFaq2.setTypeface(null, Typeface.BOLD);
        questionFaq2.setText(questionFaq);
        return converView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String answerFaq = (String)getChild(groupPosition,childPosition);
        if (convertView==null) {
            LayoutInflater inflater=(LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.faq_answer,null);
        }
        TextView answerFaq2=convertView.findViewById(R.id.descriptionFaqView);
        answerFaq2.setText(answerFaq);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
