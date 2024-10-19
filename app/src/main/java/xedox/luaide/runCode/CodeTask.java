package xedox.luaide.runCode;

public class CodeTask {

    public static void execute(Task task) {
        Thread thread =
                new Thread() {
                    @Override
                    public void run() {
                        task.execute();
                    }
                };
        thread.start();
    }

    public static void execute(Task task, Object... sync) {
        Thread thread =
                new Thread() {
                    @Override
                    public void run() {
                        for (Object obj : sync) {
                            synchronized (obj) {
                                task.execute();
                            }
                        }
                    }
                };
        thread.start();
    }

    public static interface Task {
        public void execute();
    }
}
