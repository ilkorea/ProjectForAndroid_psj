package kr.mindwing.d09_mygallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Fast campus 안드로이드 앱 개발 프로젝트 CAMP
 * MyGallery (2/3) (by mindwing)
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private static final String TAG = GalleryAdapter.class.getSimpleName();

    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<String> imageNames = new ArrayList<>();
    private int measuredWidth;

    /////////////////////////////////
    // 필수구현을 위한 오버라이딩 메서드
    /////////////////////////////////
    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new GalleryViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        holder.path = imagePaths.get(position);
        Bitmap bm = Utils.getBitmap(holder.path, true);
        int width = bm == null ? 0 : bm.getWidth();
        int height = bm == null ? 0 : bm.getHeight();

        holder.ivImage.setImageBitmap(bm);
        holder.tvTitle.setText(String.format("%s, (%dx%d)", holder.path, width, height));

        if (measuredWidth == 0) {
            holder.itemView.post(new ImageViewHeightAdjuster(holder, bm));
        } else if (bm != null) {
            double ratio = (double) bm.getHeight() / bm.getWidth();
            holder.ivImage.setMinimumHeight((int) (measuredWidth * ratio));
        }
    }

    @Override
    public int getItemCount() {
        return imagePaths.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView _recyclerView) {
        Utils.collectPicturesInfo(_recyclerView.getContext(), imagePaths, imageNames);
    }

    /////////////////////////////////////
    // 갤러리의 이미지와 정보를 담는 Holder
    /////////////////////////////////////
    class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        String path;

        public GalleryViewHolder(View listItem) {
            super(listItem);

            listItem.setOnClickListener(v -> {
                        Context ctx = v.getContext();
                        Intent intent = new Intent(ctx, DetailActivity.class);

                        intent.putExtra(DetailActivity.PATH, path);

                        // TODO Activity Transition 정보를 설정해보세요.
                        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                (Activity) ctx, ivImage, Utils.TRANSITION_NAME).toBundle();

                        ctx.startActivity(intent, bundle);
                    }
            );

            ivImage = (ImageView) listItem.findViewById(R.id.image);

            tvTitle = (TextView) listItem.findViewById(R.id.title);
        }
    }

    ////////////////////////////////////
    // 이미지의 크기에 따라 세로길이를 조절
    ////////////////////////////////////
    class ImageViewHeightAdjuster implements Runnable {

        GalleryViewHolder holder;
        int width;
        int height;

        ImageViewHeightAdjuster(GalleryViewHolder _holder, Bitmap bm) {
            holder = _holder;
            _holder.setIsRecyclable(false);

            width = bm.getWidth();
            height = bm.getHeight();
        }

        @Override
        public void run() {
            measuredWidth = holder.itemView.getWidth();

            double ratio = (double) height / width;
            holder.ivImage.setMinimumHeight((int) (measuredWidth * ratio));
            holder.setIsRecyclable(true);
        }
    }
}
