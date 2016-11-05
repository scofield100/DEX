package com.delivery2go.base.image;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import com.delivery2go.DeliveryApp;
import com.delivery2go.IImageProvider;
import com.enterlib.data.IClosable;
import com.enterlib.data.IAttachRepository;
import com.enterlib.exceptions.BusinessException;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.fields.Field;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.models.Image;

import com.enterlib.mvvm.ListViewModel;
import com.enterlib.mvvm.SelectionCommand;
import com.enterlib.threading.IWorkPost;

public class ViewModelImageList extends ListViewModel implements IClosable{

    private final FragmentImageList fragment;
    protected IImageRepository imageRepository;

    public int SelectedImageId;
    private ArrayList<Image> images;


    //protected IUserRepository userRepository;
	//protected ArrayList<User> usersOptional;
	//public List<User> getUsersOptional(){ return usersOptional; }

	public ViewModelImageList (FragmentImageList view, IImageRepository imageRepository) {
		super(view);
		
		this.imageRepository = imageRepository;   
        this.fragment = view;
		//this.userRepository=RepositoryManager.getInstance().getIUserRepository();

    
	}

    public ArrayList<Image> getImages(){
        return images;
    }

    public SelectionCommand Selection = new SelectionCommand() {
        @SuppressWarnings("unchecked")
        @Override
        public void invoke(Field field, AdapterView<?> adapterView,
                           View itemView, int position, long id) {

            onItemSelected((Image)adapterView.getItemAtPosition(position));
        }
    };
	
	@Override
	protected boolean loadAsync() throws Exception {

        images = imageRepository.getAll();

        if(SelectedImageId == 0 && images.size() > 0) {
            SelectedImageId = images.get(0).Id;
        } else if(SelectedImageId > 0) {
            Image selectedImage = null;
            for (Image img : images) {
                if (img.Id == SelectedImageId) {
                    selectedImage = img;
                    break;
                }
            }

            if(selectedImage == null && images.size() > 0){
                SelectedImageId = images.get(0).Id;
            }
        }

        return true;
	}


	@Override
	public void close() {

		this.imageRepository.close();
		//this.userRepository.close();
	}
	@Override
	protected boolean attachAsync(Object item)throws Exception {
		if(imageRepository instanceof IAttachRepository<?>){
			IAttachRepository<Image>attachedRep = (IAttachRepository<Image>) imageRepository;
			if(!attachedRep.attach((Image) item)){
				throw new InvalidOperationException("Error");
			}
			return true;
		}
		return true;
	}
	@Override
	protected boolean deleteListAsync(List<Object> items) throws Exception {
		for (int i = 0; i < items.size(); i++) {
			Image obj = (Image) items.get(i);
			imageRepository.delete(obj);
		}
		return true;
	}


	public void sendImage(final Bitmap image) {
        doAsyncWork("Sending ...", new IWorkPost() {
            @Override
            public boolean runWork() throws Exception {
                Image imageEntity = new Image();
                imageEntity.Location = UUID.randomUUID().toString();
                imageEntity.UpdateUserId = DeliveryApp.getUser().Id;
                imageEntity.UpdateDate = new Date();
                imageRepository.create(imageEntity);

                if(imageEntity.Id == 0)
                    throw  new BusinessException("Unable to save image");

                IImageProvider imageProvider = DeliveryApp.getInstance().createImageProvider();
                if(!imageProvider.saveImage(imageEntity.Id, image))
                    throw  new BusinessException("Unable to save image");

                return true;
            }

            @Override
            public void onWorkFinish(Exception workException) {
                if(workException !=null)
                    return;

                load();
            }
        });
	}

    protected void onItemSelected(Image item) {
        SelectedImageId = item.Id;
        onPropertyChange("SelectedImageId");
    }
}