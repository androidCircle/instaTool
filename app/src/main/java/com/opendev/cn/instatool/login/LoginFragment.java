package com.opendev.cn.instatool.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.opendev.cn.instagram4android.Instagram4Android;
import com.opendev.cn.instatool.GlobalInstagram;
import com.opendev.cn.instatool.R;
import com.opendev.cn.instatool.dashboard.DashboardActivity;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

/**
 * Created by root on 02/06/17.
 */

public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter presenter;

    @BindView(R.id.login_button)
    LoadingButton loginButton;
    @BindView(R.id.ig_logo)
    ImageView igLogo;
    @BindView(R.id.username_input)
    EditText usernameInput;
    @BindView(R.id.password_input)
    EditText passwordInput;
    private Unbinder unbinder;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(presenter == null) {
            presenter = new LoginPresenter(this);
        }

        presenter.subscribe();

        combineLatestEvents();
        setLoginButtonAlpha();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        loginButton.setOnClickListener(e -> {
            loginButton.startLoading();
            presenter.onLoginClick();
        });

        Picasso.with(getActivity())
                .load(R.drawable.instagram_photo_camera_logo_outline)
                .fit()
                .into(igLogo);

        return view;

    }

    private void setLoginButtonAlpha() {
        if (loginButton.isEnabled()) {
            loginButton.setAlpha(1);
        } else {
            loginButton.setAlpha(0.6F);
        }
    }

    private void combineLatestEvents() {

        Observable observable = Observable.combineLatest(
                RxTextView.textChanges(usernameInput).skip(1),
                RxTextView.textChanges(passwordInput).skip(1),
                (username, password) ->
                new ViewValuesModel(username, password));

        presenter.observeEditableFieldChanges(observable);

    }

    @Override
    public void enableButton(boolean enable) {

        loginButton.setEnabled(enable);
        setLoginButtonAlpha();

    }

    @Override
    public void makeToast(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void makeToast(int messageId) {

        Toast.makeText(getActivity(), getString(messageId), Toast.LENGTH_SHORT).show();

    }

    @Override
    public String getUsername() {
        return usernameInput.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordInput.getText().toString();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgressSpinner(boolean showSpinner) {

    }

    @Override
    public void loginFinished(Instagram4Android instagram) {
        loginButton.loadingSuccessful();
        loginButton.setAnimationEndListener(animationType -> startDashboardActivity(instagram));

    }

    private void startDashboardActivity(Instagram4Android instagram) {
        GlobalInstagram.setInstagram(instagram);
        Intent activityIntent = new Intent(getActivity(), DashboardActivity.class);
        getActivity().startActivity(activityIntent);
    }

    @Override
    public void setUserInputError() {
        usernameInput.setError(getString(R.string.username_error));
    }

    @Override
    public void setPassInputError() {
        passwordInput.setError(getString(R.string.password_error));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
