package kr.ac.kumoh.railroApplication.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {


    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @InjectView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @InjectView(R.id.share_menu_item)
    FloatingActionButton mFab;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ParallaxFragment.
     */
    Button mButton;

    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;
    static final int REQUEST_CODE = 100;
    final String item[] = {"5일", "7일"};

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCollapsingToolbar.setTitle(getString(getTitle()));
        int color = getResources().getColor(android.R.color.transparent);
        mCoordinatorLayout.setStatusBarBackgroundColor(color);

        mButton = ButterKnife.findById(getActivity(), R.id.date_picker_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // new DatePickerDialog(getActivity(), dpickerListener, year_x, month_x, day_x).show();

            }
        });


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        HomeFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                // String DatePicker = data.getStringExtra("date");
                break;
            default:
                break;
        }
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar_flexible_space_with_image;
    }

    @Override
    protected int getTitle() {
        return R.string.home;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        String dateInfo = date + "를 선택하셨습니다";
        // dateTextView.setText(date);
        Toast.makeText(getActivity(), dateInfo, Toast.LENGTH_SHORT).show();
        mButton.setText((monthOfYear + 1) + "/" + dayOfMonth);

        onTripDaySet(date);
    }

    public void onTripDaySet(final String date) {
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        ab.setTitle(date + "\n" + "내일로 여행을 몇 일간 떠나나요?");
        ab.setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichbutton) {
                switch (whichbutton) {
                    case 0:
                        Toast.makeText(getActivity(), "(" + date + ") 5일 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "(" + date + ") 7일 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
                //TODO: DB에 날짜 저장해야함
            }
        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichbutton) {

            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichbutton) {

            }
        });
        ab.show();
    }
}
