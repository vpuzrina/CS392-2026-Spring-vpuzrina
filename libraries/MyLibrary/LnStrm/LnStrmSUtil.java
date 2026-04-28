package MyLibrary.LnStrm;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class LnStrmSUtil {
    public static<T> void foritm0(LnStrm<T> fxs, Consumer<? super T> work) {
	LnStcn<T> cxs = fxs.eval0();
	while (cxs.consq()) {
	    work.accept(cxs.hd()); cxs = cxs.tl().eval0();
	}
	return;
    }

    public static<T> boolean forall0(LnStrm<T> fxs, Predicate<? super T> pred) {
	LnStcn<T> cxs = fxs.eval0();
	while (cxs.consq()) {
	    if (!pred.test(cxs.hd())) return false;
	    cxs = cxs.tl().eval0();
	}
	return true;
    }

    public static<T> LnStrm<T> filter0(LnStrm<T> fxs, Predicate<? super T> pred) {
	return new LnStrm<T>(
	  () -> {
	      LnStcn<T> cxs = fxs.eval0();
	      while (cxs.consq()) {
		  final T hd = cxs.hd();
		  final LnStrm<T> tl = cxs.tl();
		  if (pred.test(hd)) {
		      return new LnStcn<T>(hd, filter0(tl, pred));
		  } else {
		      cxs = cxs.tl().eval0();
		  }
	      }
	      return new LnStcn<T>();
	  }
       );
    }
}
