import { GearFill } from "react-bootstrap-icons";
import styles from "./AppBar.module.scss";
import { useState } from "react";
import { Dropdown, Modal, Button, SplitButton, ToggleButton } from "react-bootstrap";
import { logout } from "@/api/auth";
import { unregister } from "@/api/member";

export const MypageSettingIcon = () => {
    const [isActiveSelected, setIsActiveSelected] = useState(false);
    const [show, setShowControll] = useState(false);
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    const DropOffAndModalOnLogout = () => {
        ToggleSelected();
        setShowControll(true);
        setTitle("로그아웃");
        setContent("로그아웃 하시겠습니까?")
    }

    const DropOffAndModalOnWithdraw = () => {
        ToggleSelected();
        setShowControll(true);
        setTitle("회원 탈퇴");
        setContent("정말 탈퇴하시겠습니까?")
    }

    const ModalOff = () => {
        setShowControll(false);
    }

    const ToggleSelected = () => {
        setIsActiveSelected(!isActiveSelected)
    }

    return (
        <div>
            <GearFill className={styles.appbarIcon} onClick={ToggleSelected} />

            <Dropdown.Menu show={isActiveSelected} style={{ right: 10, marginTop: "9px" }}>
                <Dropdown.Item onClick={DropOffAndModalOnLogout}>로그아웃</Dropdown.Item>
                <Dropdown.Item onClick={DropOffAndModalOnWithdraw}>회원 탈퇴</Dropdown.Item>
                <Dropdown.Item href="/member/password/change" onClick={() => setIsActiveSelected(false)}>비밀번호 수정</Dropdown.Item>
            </Dropdown.Menu>

            <Modal show={show} onHide={ModalOff}>
                <Modal.Header>
                    <Modal.Title>{title}</Modal.Title>
                </Modal.Header>
                <Modal.Body>{content}</Modal.Body>
                <Modal.Footer>
                    <Button
                        onClick={() => {
                            if (title === "로그아웃") {
                                logout();
                            }
                            else {
                                unregister();
                            }
                        }}
                    >
                        확인
                    </Button>
                    <Button variant="secondary" onClick={ModalOff}>
                        취소
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};
