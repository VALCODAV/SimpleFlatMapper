package org.simpleflatmapper.map.mapper;

import org.simpleflatmapper.map.EnumarableMapper;
import org.simpleflatmapper.map.MappingException;
import org.simpleflatmapper.map.RowHandlerErrorHandler;
import org.simpleflatmapper.util.Enumarable;
import org.simpleflatmapper.util.EnumarableIterator;
import org.simpleflatmapper.util.RowHandler;

import java.util.Iterator;

//IFJAVA8_START
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.simpleflatmapper.util.EnumarableSpliterator;

//IFJAVA8_END


public abstract class AbstractEnumarableMapper<S, T, E extends Exception> implements EnumarableMapper<S, T, E> {

    protected final RowHandlerErrorHandler errorHandler;

    public AbstractEnumarableMapper(RowHandlerErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
	public final <H extends RowHandler<? super T>> H forEach(final S source, final H handler)
			throws E, MappingException {
        final Enumarable<T> enumarable = newEnumarableOfT(source);
        while(enumarable.next()) {
            final T t = enumarable.currentValue();
            handleT(handler, t);
        }
        return handler;
	}

    private <H extends RowHandler<? super T>> void handleT(H handler, T t) {
        try {
            handler.handle(t);
        } catch(Throwable e) {
            errorHandler.handlerError(e, t);
        }
    }

    @Override
	public final Iterator<T> iterator(S source) throws MappingException, E {
		return new EnumarableIterator<T>(newEnumarableOfT(source));
	}

    //IFJAVA8_START
	@Override
	public final Stream<T> stream(S source) throws MappingException, E {
		return StreamSupport.stream(new EnumarableSpliterator<T>(newEnumarableOfT(source)), false);
	}
    //IFJAVA8_END

    protected abstract Enumarable<T> newEnumarableOfT(S source) throws E;


}
