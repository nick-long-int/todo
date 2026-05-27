package com.emobile.springtodo.repo;

import com.emobile.springtodo.model.Task;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private final SessionFactory sessionFactory;

    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    public Task save(Task task){
        getCurrentSession().persist(task);
        return task;
    }


    public Task findById(Long id){
        return getCurrentSession().get(Task.class, id);
    }


    public List<Task> findTasksPageable(int page, int size){
        return getCurrentSession().createQuery("from Task t order by t.id asc", Task.class)
            .setFirstResult(page * size)
            .setMaxResults(size).getResultList();
    }

    public void deleteById(Long id){
        getCurrentSession().remove(findById(id));
    }

    public Long count(){
        return getCurrentSession().createQuery("select count(*) from Task", Long.class).getSingleResult();
    }
}
