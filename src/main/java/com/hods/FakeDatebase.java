/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods;

import com.hods.meeting.recorder.impl.RecordingElem;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.hibernate.Session;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author maher
 */
public class FakeDatebase implements Database{
    private Session session;
    private AtomicLong atomicLong;
    public FakeDatebase() {
        atomicLong = new AtomicLong();
        session = mock(Session.class);
        when(session.save(any())).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object o = invocation.getArguments()[0];
                if(o instanceof RecordingElem){
                    RecordingElem e = (RecordingElem)o;
                    e.setDbId(atomicLong.getAndIncrement());
                }
                return o;
            }
        });
    }
    
    @Override
    public Session getCurrentSession() {
        return session;
    }
    
}
