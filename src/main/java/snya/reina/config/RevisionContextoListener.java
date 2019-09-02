package snya.reina.config;

import java.util.Date;

import org.hibernate.envers.RevisionListener;

public class RevisionContextoListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        Revision revision = (Revision) revisionEntity;
        revision.setFecha( new Date() );
        revision.setUsuario( "usuario" );
        revision.setSistema( 3 );
        revision.setSector( 123 );
        revision.setRol( 3 );
    }
}