package com.sviryd.chat.domain;

public final class Views {
    public interface Id {
    }

    public interface Username {
    }

    public interface CreationLDT {
    }

    public interface Male {
    }

    public interface Enabled {
    }

    public interface User extends Id, Username, Male {
    }

    public interface Users extends Id, Username, Male {
    }


    public interface Author {
    }

    public interface AuthorId {
    }

    public interface Message {
    }

    public interface Messages extends Id, AuthorId, CreationLDT, Message {
    }
}
