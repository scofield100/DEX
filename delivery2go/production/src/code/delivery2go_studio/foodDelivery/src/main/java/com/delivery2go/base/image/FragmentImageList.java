package com.delivery2go.base.image;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.delivery2go.ThumbailDrawableConverter;
import com.enterlib.StringUtils;
import com.enterlib.filtering.SearchViewFilterController;
import com.enterlib.mvvm.ActionBarFilterableFragment;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.fields.ListField;
import com.enterlib.databinding.BindingResources;
import com.enterlib.filtering.FilterOp;
import com.enterlib.filtering.SearchViewFilterController;
import com.enterlib.filtering.SpinnerFilterCondition;
import com.enterlib.filtering.StringFilterCondition;
import com.enterlib.filtering.SwitchCondition;
import com.enterlib.filtering.DateFilterCondition;
import com.enterlib.filtering.TimeFilterCondition;
import com.enterlib.filtering.DoubleFilterCondition;
import com.enterlib.filtering.IntegerFilterCondition;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.delivery2go.base.data.RepositoryManager;
import com.enterlib.support.ActivityPickFile;
import com.enterlib.widgets.ProgressLayout;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.image.ViewModelImageList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.Image})
public class FragmentImageList extends ActionBarFilterableFragment {

    private static final int TAKE_PICTURE = 1;
    private static final int BROWSE_PICTURE = 2;

    private static final int MAX_WIDTH = 800;

    private File outputFileUri;

    private ProgressLayout selected_image_progress;

    public FragmentImageList(){

    }

    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_image_list, menu);
		if(getActivity().getIntent().getBooleanExtra(com.enterlib.mvvm.ActionBarFilterableFragment.ATTACH_MODE, false)){
			menu.findItem(R.id.delete).setTitle(R.string.select);
			menu.findItem(R.id.delete).setIcon(R.drawable.ic_action_list_2);

			menu.findItem(R.id.create).setEnabled(false);
			menu.findItem(R.id.create).setVisible(false);

			onInitDelete(menu);
		} else {

			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Image}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}else{
				onInitDelete(menu);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Image}, Access.CREATE)){
				menu.findItem(R.id.create).setEnabled(false);
				menu.findItem(R.id.create).setVisible(false);
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			outputFileUri = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "picture_view_capture.jpg");
			outputFileUri.setWritable(true);
			outputFileUri.setReadable(true);
		}

		setDeleteItemId(R.id.delete);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_image_fragment_list, container, false);
		ProgressLayout progress = (ProgressLayout)view.findViewById(R.id.container);
		setProgressLayout(progress);

        selected_image_progress = (ProgressLayout)view.findViewById(R.id.selected_image_container);
		return view;
	}

    public void showSelectedImageProgress(){
        selected_image_progress.setMessage(getString(R.string.loading));
        selected_image_progress.showProgress();
    }

    public void dismisSelectedImageProgress(){
        selected_image_progress.closeProgress();
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.attach:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityImageList.class)
					.putExtra(ActivityImageDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityImageDetails.ID))
					.putExtra("ATTACH_MODE",true), FragmentImageEdit.IMAGE_EDITED);
				return true;
			case R.id.create:
				//startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityImageEdit.class), FragmentImageEdit.IMAGE_EDITED);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.dialog_obtener_imagen_title);
                builder.setItems(new String[]{ getString(R.string.tomar_con_la_camara), getString(R.string.buscar_en_el_dispositivo)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            //lanzar camera activity
                            takePicture();
                        }else{
                            //lanzar pick file activity
                            startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityPickFile.class), BROWSE_PICTURE);
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
                return true;
		}
		return super.onOptionsItemSelected(item);
	}

    //**Take a picture from the camera activity*/
    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(outputFileUri!=null){
            Uri uriStoreFile = Uri.fromFile(outputFileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriStoreFile);
        }

        startActivityForResult(intent, TAKE_PICTURE);
    }

	@Override
	protected BindingResources getBindingResources() {
		return new BindingResources()
		.put("DateConverter", new com.enterlib.converters.DateToStringConverter())
        .put("ImageConverter", new ThumbailDrawableConverter((int)getResources().getDimension(R.dimen.spinner_image),(int)getResources().getDimension(R.dimen.spinner_image)))
        .put("SelectedImageConverter", new ThumbailDrawableConverter(selected_image_progress))
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_IMAGE");
		IImageRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_IMAGE");
		}

		if(factoryCls!=null){
			try {
				repository=(IImageRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIImageRepository();
		}
		return new ViewModelImageList(this, repository);
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
		return null;
	}

	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		if(requestCode == EntityCursorViewModel.ENTITY_DETAILS){
			if(getActivity().getIntent().getBooleanExtra("ATTACH_MODE", false)){
				attach(data);
				return;
			}
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityImageDetails.class);
			i.putExtra(ActivityImageDetails.ID, new int[]{((Image)data).Id});
			startActivityForResult(i,FragmentImageEdit.IMAGE_EDITED);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {

            Bitmap image = null;

            if (requestCode == TAKE_PICTURE) {
                image = getImage(data);
            } else if (requestCode == BROWSE_PICTURE) {
                Uri content = data.getData();
                String file = content.getPath();
                image = BitmapFactory.decodeFile(file);
            }

            if (image != null) {
                image = scaleBitmap(image);
                ViewModelImageList viewModel = (ViewModelImageList) getViewModel();
                viewModel.sendImage(image);
            }
        }

		if(resultCode == FragmentImageEdit.IMAGE_EDITED){
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onAttached(Object item, Exception exception) {
		if(exception !=null)
			return;
		getActivity().setResult(FragmentImageEdit.IMAGE_EDITED);
		getActivity().finish();
	}

	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentImageEdit.IMAGE_EDITED);
		load();
	}

    private Bitmap getImage(Intent data){
        if (data != null && data.hasExtra("data")) {
            return  data.getParcelableExtra("data");
        }else if(outputFileUri!=null){
            //Decode the image from the TakePictureActity
            Bitmap bmp = BitmapFactory.decodeFile(outputFileUri.getPath());
            //bmp = Bitmap.createScaledBitmap(bmp, maxImageWidth, maxImageHeight, true);
            outputFileUri.delete();
            return bmp;
        }
        else
            return null;
    }

    private Bitmap scaleBitmap(Bitmap src){
        int width = MAX_WIDTH;
        if(src.getWidth() <= width )
            return src;

        int height = ( src.getHeight() * width) / src.getWidth();
        return Bitmap.createScaledBitmap(src, width, height, true);
    }

}
