package demo.todolist.helper.solutions;

public enum FindingSolutionStatus {
    IN_PROGRESS(1),
    COMPLETED(2),
    FAILED(3);

    private final int value;

    FindingSolutionStatus(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
