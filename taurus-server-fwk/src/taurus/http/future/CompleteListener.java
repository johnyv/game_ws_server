package taurus.http.future;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CompleteListener {
    public abstract void complete(Object arg);

    public void exception(Throwable t) {
        log.error(String.valueOf(t.getStackTrace()));
    }
}
