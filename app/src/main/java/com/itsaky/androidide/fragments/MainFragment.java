package com.itsaky.androidide.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itsaky.androidide.EditorActivity;
import com.itsaky.androidide.PreferencesActivity;
import com.itsaky.androidide.R;
import com.itsaky.androidide.TerminalActivity;
import com.itsaky.androidide.databinding.FragmentMainBinding;
import com.itsaky.androidide.projects.ProjectManager;

import java.io.File;

public class MainFragment extends BaseFragment
    implements WizardFragment.OnProjectCreatedListener, View.OnClickListener {

  public static final String TAG = "MainFragmentTag";

  private FragmentMainBinding binding;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentMainBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.createProject.setOnClickListener(this);
    binding.openProject.setOnClickListener(this);
    binding.openTerminal.setOnClickListener(this);
    binding.gotoPreferences.setOnClickListener(this);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  @Override
  public void onClick(View p1) {
    if (p1.getId() == binding.createProject.getId()) {
      showCreateProject();
    } else if (p1.getId() == binding.gotoPreferences.getId()) {
      gotoSettings();
    } else if (p1.getId() == binding.openProject.getId()) {
      pickDirectory();
    } else if (p1.getId() == binding.openTerminal.getId()) {
      startActivity(new Intent(requireActivity(), TerminalActivity.class));
    }
  }

  private void pickDirectory() {
    pickDirectory(this::openProject);
  }

  private void showCreateProject() {

    WizardFragment wizardFragment = new WizardFragment();
    wizardFragment.setOnProjectCreatedListener(this);
    getParentFragmentManager()
        .beginTransaction()
        .add(R.id.container, wizardFragment, WizardFragment.TAG)
        .addToBackStack(null)
        .commit();
  }

  @Override
  public void openProject(@NonNull File root) {
    ProjectManager.INSTANCE.setProjectPath(root.getAbsolutePath());
    startActivity(new Intent(requireActivity(), EditorActivity.class));
  }

  private void gotoSettings() {
    startActivity(new Intent(requireActivity(), PreferencesActivity.class));
  }
}
