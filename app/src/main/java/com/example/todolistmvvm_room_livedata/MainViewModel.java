package com.example.todolistmvvm_room_livedata;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private NoteDatabase noteDatabase;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
//    private int count = 0;
//    private MutableLiveData<Integer> countId = new MutableLiveData<>();
//    public LiveData<Integer> getCount() {
//        return countId;
//    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getInstance(application);
    }

    public void remove (Note note) {
        Disposable disposable = noteDatabase.notesDao().removeAt(note.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.i("MainViewModel", "remove");
//                        refreshList();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.i("MainViewModel", "error while remove");
                    }
                });
        compositeDisposable.add(disposable);
    }

//    private Completable removeRx (Note note) {
//        return Completable.fromAction(new Action() {
//            @Override
//            public void run() throws Throwable {
//                noteDatabase.notesDao().removeAt(note.getId());
//            }
//        });
//    }

    public LiveData<List<Note>> getNotes() {
        return noteDatabase.notesDao().getNotes();
    }

//    public void refreshList() {
//        Disposable disposable = noteDatabase.notesDao().getNotes()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<Note>>() {
//                    @Override
//                    public void accept(List<Note> notesFromDB) throws Throwable {
//                        notes.setValue(notesFromDB);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Throwable {
//                        Log.i("MainViewModel", "error while refresh");
//                    }
//                });
//        compositeDisposable.add(disposable);
//    }

//    private Single<List<Note>> getNotesRx() {
//        return Single.fromCallable(new Callable<List<Note>>() {
//            @Override
//            public List<Note> call() throws Exception {
//                return noteDatabase.notesDao().getNotes();
//            }
//        });
//    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    //    public void showCount() {
//        count++;
//        countId.setValue(count);
//    }
}
