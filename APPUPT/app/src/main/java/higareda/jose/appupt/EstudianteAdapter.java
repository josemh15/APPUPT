package higareda.jose.appupt;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class EstudianteAdapter extends RecyclerView.Adapter<EstudianteAdapter.ViewHolder> {

    List<Estudiante> lista;

    public EstudianteAdapter(List<Estudiante> lista){
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvNombre, tvCarrera, tvEstado;
        ImageView imgFoto;

        public ViewHolder(View itemView){
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCarrera = itemView.findViewById(R.id.tvCarrera);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            imgFoto = itemView.findViewById(R.id.imgFoto);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_estudiante,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Estudiante e = lista.get(position);

        holder.tvNombre.setText(e.nombre);
        holder.tvCarrera.setText(e.carrera);
        holder.tvEstado.setText("Estado: " + e.estado);

        // URL de la imagen
        if(e.foto != null && !e.foto.isEmpty()){

            String urlFoto = "http://192.168.0.108/credenciales/" + e.foto;

            Glide.with(holder.itemView.getContext())
                    .load(urlFoto)
                    .placeholder(R.drawable.usuario)
                    .into(holder.imgFoto);

        }else{

            holder.imgFoto.setImageResource(R.drawable.usuario);

        }


        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), DetalleAlumnoActivity.class);

            intent.putExtra("id", e.id);
            intent.putExtra("nombre", e.nombre);
            intent.putExtra("carrera", e.carrera);
            intent.putExtra("estado", e.estado);
            intent.putExtra("foto", e.foto);

            v.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

}